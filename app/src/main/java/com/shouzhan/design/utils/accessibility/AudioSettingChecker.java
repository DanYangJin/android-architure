package com.shouzhan.design.utils.accessibility;

import android.content.Context;

import com.fshows.android.stark.utils.FsLogUtil;

/**
 * @author danbin
 * @version AudioSettingChecker.java, v 0.1 2020-05-12 9:30 PM danbin
 * 语音开关设置类
 */
public class AudioSettingChecker extends AbstractSettingChecker {

    public AudioSettingChecker(Context mContext) {
        super(mContext);
    }

    @Override
    public AudioDiagnosisResult startDiagnosis(OnCheckCallback callback) {
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 语音开关设置开始 <<<");
        FsLogUtil.error(AudioSettingConstants.AUDIO_DIAGNOSIS_TAG, ">>> 语音开关设置结束 <<<");
        return callback.onCheckResult();
    }

}
