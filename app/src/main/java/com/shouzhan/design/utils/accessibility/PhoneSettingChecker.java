package com.shouzhan.design.utils.accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.Keep;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.utils.OSUtil;

import org.apache.commons.collections4.CollectionUtils;

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
 * 系统白名单设置类
 */
public class PhoneSettingChecker extends AbstractSettingChecker {

    public static final int MAX_PROGRESS = 200;

    public volatile Queue<SettingTask> mSettingTasks = new LinkedList<>();

    private volatile CountDownLatch mCountDownLatch;

    private volatile PhoneSettingCheckResult mPhoneSettingCheckResult = new PhoneSettingCheckResult();

    private IntentFilter mIntentFilter = new IntentFilter();

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
                String stringExtra = intent.getStringExtra(AudioSettingConstants.TASK_NAME);
                String stringExtra2 = intent.getStringExtra(AudioSettingConstants.VALUE);
                FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "收到返回值 >>> " + stringExtra + " = " + stringExtra2);
                PhoneSettingChecker.this.mPhoneSettingCheckResult.addSetting(stringExtra, stringExtra2);
            } else if (AudioSettingConstants.ACTION_PHONE_SETTING_TASK_DONE.equals(action)) {
                int intExtra = intent.getIntExtra(AudioSettingConstants.TASK_ID, 0);
                FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "收到任务执行完毕回执 >>> taskId = " + intExtra);
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
        super(context);
        this.mIntentFilter.addAction(AudioSettingConstants.ACTION_PHONE_SETTING);
        this.mIntentFilter.addAction(AudioSettingConstants.ACTION_PHONE_SETTING_TASK_DONE);
    }

    @Override
    public AudioDiagnosisResult startDiagnosis(OnCheckCallback callback) {
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 系统白名单设置开始 <<<");
        if (CollectionUtils.isNotEmpty(mSettingTasks)) {
            LocalBroadcastManager.getInstance(mContext).registerReceiver(this.mReceiver, this.mIntentFilter);
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
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(this.mReceiver);
        } else {
            mPhoneSettingCheckResult.setCheckSuccess(true);
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(AudioSettingConstants.ACTION_PHONE_SETTING_FINISH));
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 手机设置检查结束 <<<");
        resetPhoneSettingChecker();
        AudioDiagnosisResult result = callback.onCheckResult();
        result.setResult(mPhoneSettingCheckResult);
        return result;
    }

    private void initPhoneSettingChecker() {
        mTaskCount = this.mSettingTasks.size();
        FloatWindowView.getInstance().showFloatWindowView();
        startProgressCountDown();
        executeTask();
    }

    private void startProgressCountDown() {
        if (mDisposable == null || mDisposable.isDisposed()) {
            mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) {
                    if (PhoneSettingChecker.this.mCurProgress < MAX_PROGRESS - 1) {
                        FloatWindowView.getInstance().setProgress(PhoneSettingChecker.updateCurProgress(PhoneSettingChecker.this));
                    }
                }
            });
        }
    }

    public void executeTask() {
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "launchTask线程 >>> " + Thread.currentThread().getId());
        mCountDownLatch.countDown();
        if (mSettingTasks == null || mSettingTasks.isEmpty()) {
            this.mPhoneSettingCheckResult.setCheckSuccess(true);
            return;
        }
        mCurTask = mSettingTasks.poll();
        String builder = "准备执行任务 >>> " +
                (this.mCurTask == null ? "" : this.mCurTask.getTaskName());
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, builder);
        Intent intent = new Intent();
        intent.setAction(AudioSettingConstants.ACTION_PHONE_SETTING_TASK);
        intent.putExtra(AudioSettingConstants.TASK, this.mCurTask);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }


    public void clearSettingTasks() {
        mSettingTasks.clear();
    }

    public void addSettingTasks(Queue<SettingTask> queue) {
        if (queue != null && !queue.isEmpty()) {
            mSettingTasks.addAll(queue);
        }
    }

    private void resetPhoneSettingChecker() {
        FloatWindowView.getInstance().setProgress(mCurProgress);
        mCurProgress = 0;
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Keep
    public static class PhoneSettingCheckResult {
        /**
         * 检查状态
         */
        private boolean isCheckSuccess;
        /**
         * 手机设置状态保存
         */
        private HashMap<String, Object> phoneSetting = new HashMap<>();
        /**
         * 当前任务
         */
        private SettingTask curTask;

        /**
         * 保存一键优化后权限状态
         * */
        public void addSetting(String str, Object obj) {
            phoneSetting.put(str, obj);
        }

        public boolean isCheckSuccess() {
            return isCheckSuccess;
        }

        public void setCheckSuccess(boolean isCheckSuccess) {
            this.isCheckSuccess = isCheckSuccess;
        }

        public SettingTask getCurTask() {
            return curTask;
        }

        public void setCurTask(SettingTask curTask) {
            this.curTask = curTask;
        }
    }


}
