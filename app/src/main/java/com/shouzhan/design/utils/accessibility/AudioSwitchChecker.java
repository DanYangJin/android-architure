package com.shouzhan.design.utils.accessibility;

import android.content.Context;

import com.fshows.android.stark.utils.FsLogUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author danbin
 * @version AudioSettingChecker.java, v 0.1 2020-05-12 9:30 PM danbin
 * 语音开关设置类
 */
public class AudioSwitchChecker extends AbstractSettingChecker {

    public CountDownLatch mCountDownLatch;

    public AudioSwitchChecker(Context mContext) {
        super(mContext);
    }

    @Override
    public AudioDiagnosisResult startDiagnosis(OnCheckCallback callback) {
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 语音开关设置开始 <<<");
        mCountDownLatch = new CountDownLatch(1);
        requestAudioSetting();
        try {
            mCountDownLatch.await(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 语音开关设置结束 <<<");
        return callback.onCheckResult();
    }

    public void requestAudioSetting() {
        mCountDownLatch.countDown();
    }

}
