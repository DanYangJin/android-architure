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
public class SingleTask implements Serializable {

    private Queue<SingleStep> stepQueue = new LinkedList();
    private int taskId;
    private String taskName;

    public SingleTask addStep(SingleStep step) {
        this.stepQueue.add(step);
        return this;
    }

    public SingleTask addStep(String actionValue, int action) {
        addStep(actionValue, action, null, null, true);
        return this;
    }

    public SingleTask addStepById(String viewId, int action) {
        addStep(new SingleStep(viewId, action));
        return this;
    }

    public SingleTask addStep(String actionValue, int action, boolean extraMatch, boolean boundMatch) {
        addStep(new SingleStep(actionValue, action, extraMatch, boundMatch));
        return this;
    }

    public SingleTask addStep(String actionValue, int action, String viewId) {
        addStep(new SingleStep(actionValue, action, viewId, true));
        return this;
    }

    public SingleTask addStep(String actionValue, int action, Bundle bundle) {
        addStep(actionValue, action, bundle, null, true);
        return this;
    }

    public SingleTask addStep(String actionValue, int action, boolean extraMatch) {
        addStep(actionValue, action, null, null, extraMatch);
        return this;
    }

    public SingleTask addStep(String actionValue, int action, Uri uri) {
        addStep(actionValue, action, null, uri, true);
        return this;
    }

    public SingleTask addStep(String actionValue, int action, Bundle bundle, Uri uri, boolean extraMatch) {
        addStep(new SingleStep(actionValue, action, bundle, uri, extraMatch));
        return this;
    }

    public Queue<SingleStep> getStepQueue() {
        return this.stepQueue;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public SingleTask setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public SingleTask setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

}
