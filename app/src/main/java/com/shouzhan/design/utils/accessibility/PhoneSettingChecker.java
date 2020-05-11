package com.shouzhan.design.utils.accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.Keep;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.ui.home.CommonH5Activity;
import com.shouzhan.design.utils.Constants;
import com.shouzhan.design.utils.OSUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author danbin
 * @version PhoneSettingChecker.java, v 0.1 2020-05-09 9:52 AM danbin
 * 系统白名单设置检查类
 */
public class PhoneSettingChecker {

    public static final int MAX_PROGRESS = 200;

    public volatile Queue<SettingTask> mSettingTasks = new LinkedList<>();

    private volatile CountDownLatch mCountDownLatch;

    private volatile PhoneSettingCheckResult mPhoneSettingCheckResult = new PhoneSettingCheckResult();

    private IntentFilter mIntentFilter = new IntentFilter();

    private Context mContext;
    private int mTaskCount;
    private int mCurProgress;
    private SettingTask mCurTask;
    private Disposable mDisposable;

    static int updateCurProgress(PhoneSettingChecker phoneSettingChecker) {
        int i = phoneSettingChecker.mCurProgress + 1;
        phoneSettingChecker.mCurProgress = i;
        return i;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioSettingConstants.ACTION_PHONE_SETTING.equals(action)) {
                String stringExtra = intent.getStringExtra("taskName");
                String stringExtra2 = intent.getStringExtra("value");
                FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "收到返回值 >>> " + stringExtra + " = " + stringExtra2);
                PhoneSettingChecker.this.mPhoneSettingCheckResult.addSetting(stringExtra, stringExtra2);
            } else if (AudioSettingConstants.ACTION_PHONE_SETTING_TASK_DONE.equals(action)) {
                int intExtra = intent.getIntExtra("taskId", 0);
                FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG,"收到任务执行完毕回执 >>> taskId = " + intExtra);
                double b = (PhoneSettingChecker.this.mTaskCount - PhoneSettingChecker.this.mSettingTasks.size());
                Double.isNaN(b);
                double b2 = PhoneSettingChecker.this.mTaskCount;
                Double.isNaN(b2);
                int i = (int) ((b * 200.0d) / b2);
                if (i > PhoneSettingChecker.this.mCurProgress) {
                    PhoneSettingChecker.this.mCurProgress = i;
                }
                FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 自动化设置进度： <<<" + mCurProgress);
                PhoneSettingChecker.this.executeTask();
            }
        }
    };

    public PhoneSettingChecker(Context context) {
        this.mContext = context;
        this.mIntentFilter.addAction(AudioSettingConstants.ACTION_PHONE_SETTING);
        this.mIntentFilter.addAction(AudioSettingConstants.ACTION_PHONE_SETTING_TASK_DONE);
    }

    public AudioDiagnosisResult startPhoneSettingChecker() {
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG,"check线程 >>> " + Thread.currentThread().getId());
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG,">>> 手机设置检查开始 <<<");
        if (!OSUtil.isFuntouchRom() || (mSettingTasks != null && !mSettingTasks.isEmpty())) {
            LocalBroadcastManager.getInstance(mContext).registerReceiver(this.mReceiver, this.mIntentFilter);
            if (mSettingTasks == null || mSettingTasks.isEmpty()) {
                mPhoneSettingCheckResult.setCheckSuccess(true);
            } else {
                mCountDownLatch = new CountDownLatch(mSettingTasks.size() + 1);
                initPhoneSettingChecker();
                try {
                    mCountDownLatch.await(OSUtil.isMiuiRom() ? 130 : 120, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mCurTask != null) {
                    mPhoneSettingCheckResult.setCurTask(this.mCurTask);
                }
                mSettingTasks.clear();
            }
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(this.mReceiver);
        } else {
            String h5Url;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                h5Url = "https://i.meituan.com/awp/hfe/block/26fca4aca3aa/25657/index.html";
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                h5Url = "https://i.meituan.com/awp/hfe/block/1188318d4977/25651/index.html";
            } else {
                h5Url = "https://i.meituan.com/awp/hfe/block/cc60f36d2e8c/25659/index.html";
            }
            Intent intent = new Intent(mContext, CommonH5Activity.class);
            intent.putExtra(Constants.EXTRA_COMMON_H5_URL, h5Url);
            mContext.startActivity(intent);
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(AudioSettingConstants.ACTION_PHONE_SETTING_FINISH));
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 手机设置检查结束 <<<");
        resetPhoneSettingChecker();
        AudioDiagnosisResult result = new AudioDiagnosisResult();
        result.setResult(mPhoneSettingCheckResult);
        return result;
    }

    private void initPhoneSettingChecker() {
        mTaskCount = this.mSettingTasks.size();
//        FloatWindowView.getInstance().showFloatWindowView();
        startProgressCountDown();
        executeTask();
    }

    private void startProgressCountDown() {
        if (this.mDisposable == null || this.mDisposable.isDisposed()) {
            this.mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    if (PhoneSettingChecker.this.mCurProgress < MAX_PROGRESS - 1) {
                        FloatWindowView.getInstance().setProgress(PhoneSettingChecker.updateCurProgress(PhoneSettingChecker.this));
                    }
                }
            });
        }
    }

    public void executeTask() {
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "launchTask线程 >>> " + Thread.currentThread().getId());
        this.mCountDownLatch.countDown();
        if (this.mSettingTasks == null || this.mSettingTasks.isEmpty()) {
            this.mPhoneSettingCheckResult.setCheckSuccess(true);
            return;
        }
        this.mCurTask = this.mSettingTasks.poll();
        StringBuilder builder = new StringBuilder();
        builder.append("准备执行任务 >>> ");
        builder.append(this.mCurTask == null ? "" : this.mCurTask.getTaskName());
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, builder.toString());
        Intent intent = new Intent();
        intent.setAction(AudioSettingConstants.ACTION_PHONE_SETTING_TASK);
        intent.putExtra("task", this.mCurTask);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }


    public void clearSettingTasks() {
        this.mSettingTasks.clear();
    }

    public void addSettingTasks(Queue<SettingTask> queue) {
        if (queue != null && !queue.isEmpty()) {
            this.mSettingTasks.addAll(queue);
        }
    }

    private void resetPhoneSettingChecker() {
        FloatWindowView.getInstance().setProgress(this.mCurProgress);
        this.mCurProgress = 0;
        if (this.mDisposable != null && !this.mDisposable.isDisposed()) {
            this.mDisposable.dispose();
        }
    }

    @Keep
    public static class PhoneSettingCheckResult {
        /**
         * 检查状态
         * */
        private boolean isCheckSuccess;
        /**
         * 手机设置状态保存
         * */
        private HashMap<String, Object> phoneSetting = new HashMap<>();
        /**
         * 当前任务
         * */
        private SettingTask curTask;

        public void addSetting(String str, Object obj) {
            this.phoneSetting.put(str, obj);
        }

        public boolean isCheckSuccess() {
            return this.isCheckSuccess;
        }

        public void setCheckSuccess(boolean z) {
            this.isCheckSuccess = z;
        }

        public SettingTask getCurTask() {
            return this.curTask;
        }

        public void setCurTask(SettingTask curTask) {
            this.curTask = curTask;
        }
    }


}
