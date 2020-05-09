package com.shouzhan.design.utils.accessibility;

import android.net.Uri;
import android.os.Bundle;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author danbin
 * @version SingleTask.java, v 0.1 2020-05-06 7:39 PM danbin
 * 辅助功能单个任务
 */
public class SettingTask implements Serializable {

    private Queue<SettingStep> stepQueue = new LinkedList();
    private int taskId;
    private String taskName;

    public SettingTask addStep(SettingStep step) {
        this.stepQueue.add(step);
        return this;
    }

    public SettingTask addStep(String actionValue, int action) {
        addStep(actionValue, action, null, null, true);
        return this;
    }

    public SettingTask addStepById(String viewId, int action) {
        addStep(new SettingStep(viewId, action));
        return this;
    }

    public SettingTask addStep(String actionValue, int action, boolean extraMatch, boolean boundMatch) {
        addStep(new SettingStep(actionValue, action, extraMatch, boundMatch));
        return this;
    }

    public SettingTask addStep(String actionValue, int action, String viewId) {
        addStep(new SettingStep(actionValue, action, viewId, true));
        return this;
    }

    public SettingTask addStep(String actionValue, int action, Bundle bundle) {
        addStep(actionValue, action, bundle, null, true);
        return this;
    }

    public SettingTask addStep(String actionValue, int action, boolean extraMatch) {
        addStep(actionValue, action, null, null, extraMatch);
        return this;
    }

    public SettingTask addStep(String actionValue, int action, Uri uri) {
        addStep(actionValue, action, null, uri, true);
        return this;
    }

    public SettingTask addStep(String actionValue, int action, Bundle bundle, Uri uri, boolean extraMatch) {
        addStep(new SettingStep(actionValue, action, bundle, uri, extraMatch));
        return this;
    }

    public Queue<SettingStep> getStepQueue() {
        return this.stepQueue;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public SettingTask setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public SettingTask setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

}
