package com.shouzhan.design.utils.accessibility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.fshows.android.stark.utils.CommonThreadPoolExecutor;
import com.fshows.android.stark.utils.FsLogUtil;

import java.util.Queue;

/**
 * @author danbin
 * @version AudioDiagnosisManager.java, v 0.1 2020-05-09 8:56 AM danbin
 * 语音设置管理类
 */
public class AudioDiagnosisManager {

    private CommonThreadPoolExecutor mPoolExecutor;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private Activity mActivity;
    private PhoneSettingChecker mPhoneSettingChecker;
    private AudioDiagnosisListener mAudioDiagnosisListener;
    private long mStartDiagnosisTime;

    public static class AudioDiagnosisManagerBuilder {

        public static AudioDiagnosisManager audioDiagnosisManager = new AudioDiagnosisManager();

    }

    public static AudioDiagnosisManager getInstance() {
        return AudioDiagnosisManagerBuilder.audioDiagnosisManager;
    }

    private AudioDiagnosisManager() {
        mPoolExecutor = new CommonThreadPoolExecutor();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (AudioSettingConstants.ACTION_PHONE_SETTING_SERVICE_CONNECT.equals(intent.getAction())) {
                    AudioDiagnosisManager.this.executeDiagnosisTask(true);
                }
            }
        };
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(AudioSettingConstants.ACTION_PHONE_SETTING_SERVICE_CONNECT);
    }

    /**
     * 开始语音诊断任务
     */
    public void startDiagnosisTask(Activity activity) {
        initDiagnosisTask(activity);
        registerReceiver();
        Queue<SettingTask> mSettingTasks = TaskFactory.getSettingTask(mActivity);
        if (mSettingTasks == null || mSettingTasks.isEmpty()) {
            FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "暂无自动优化任务");
            if (mAudioDiagnosisListener != null) {
                mAudioDiagnosisListener.onDiagnosisCallback(false, 0, null);
            }
        } else if (AccessibilityUtil.isAccessibilitySettingsOn(mActivity)) {
            executeDiagnosisTask(false);
        } else {
            // 打开辅助功能页面
            // 打开引导页面
        }
    }

    public void initDiagnosisTask(Activity activity) {
        this.mActivity = activity;
        this.mPhoneSettingChecker = new PhoneSettingChecker(activity);
    }

    public void registerReceiver() {
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(mReceiver, mIntentFilter);
    }

    public void unRegisterReceiver() {
        if (this.mReceiver != null) {
            LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(mReceiver);
        }
    }

    /**
     * 开始执行语音诊断任务
     *
     * @param isOpen 是否开通辅助功能
     * @return
     */
    public void executeDiagnosisTask(final boolean isOpen) {
        this.mPhoneSettingChecker.clearSettingTasks();
        this.mPhoneSettingChecker.addSettingTasks(TaskFactory.getSettingTask(mActivity, isOpen));
        this.mPoolExecutor.execute(() -> {
            mStartDiagnosisTime = System.currentTimeMillis();
            AudioDiagnosisManager.this.getPhoneSettingCallback(mPhoneSettingChecker.startPhoneSettingChecker());
            AudioDiagnosisManager.this.mActivity.runOnUiThread(() -> {
//                FloatWindowView.getInstance().removeFloatWindowView();
            });
        });
    }

    public void getPhoneSettingCallback(AudioDiagnosisResult result) {
        final PhoneSettingChecker.PhoneSettingCheckResult checkResult = result.getResult();
        boolean isCheckSuccess = checkResult != null && checkResult.isCheckSuccess();
        long currentTimeMillis = System.currentTimeMillis() - mStartDiagnosisTime;
        mActivity.runOnUiThread(() -> {
            if (mAudioDiagnosisListener != null) {
                mAudioDiagnosisListener.onDiagnosisCallback(isCheckSuccess, currentTimeMillis, checkResult != null ? checkResult.getCurTask() : null);
            }
            skipDiagnosisSuccessPage();
        });
        unRegisterReceiver();
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, "语音诊断任务执行结束 >>> result = %s, time = %dms", isCheckSuccess, currentTimeMillis);
    }

    /**
     * TODO bugfix跳转到诊断成功页面
     */
    private void skipDiagnosisSuccessPage() {

    }

    public void setOnAudioDiagnosisListener(AudioDiagnosisListener listener) {
        this.mAudioDiagnosisListener = listener;
    }

    public void removeOnAudioDiagnosisListener() {
        this.mAudioDiagnosisListener = null;
    }

    /**
     * 退出页面销毁Task
     */
    public void resetDiagnosisTask() {
        removeOnAudioDiagnosisListener();
        unRegisterReceiver();
    }

}
