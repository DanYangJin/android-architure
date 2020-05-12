package com.shouzhan.design.utils.accessibility;

import android.content.Context;

/**
 * @author danbin
 * @version AbstractSettingChecker.java, v 0.1 2020-05-12 9:29 PM danbin
 */
public abstract class AbstractSettingChecker {

    protected Context mContext;

    public AbstractSettingChecker(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 开启语音诊断
     *
     * @param callback
     * @return
     */
    public abstract AudioDiagnosisResult startDiagnosis(OnCheckCallback callback);

}
