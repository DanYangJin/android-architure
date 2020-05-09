package com.shouzhan.design.utils.accessibility;

/**
 * @author danbin
 * @version AudioDiagnosisListener.java, v 0.1 2020-04-28 9:31 AM danbin
 */
public interface AudioDiagnosisListener {

    /**
     * 语音自动化诊断回调
     *
     * @param success 是否成功
     * @param totalTime 总耗时
     * @param task 当前任务
     * @return
     * */
    void onDiagnosisCallback(boolean success, long totalTime, SettingTask task);

}
