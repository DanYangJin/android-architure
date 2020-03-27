package com.shouzhan.design.push;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author danbin
 */
@Keep
public class Task implements Serializable {

    private Queue<Step> stepQueue = new LinkedList();
    private int taskId;
    private String taskName;

    public Task addStep(Step step) {
        this.stepQueue.add(step);
        return this;
    }

    public Task addStep(String actionValue, int action) {
        addStep(actionValue, action, null, null, true);
        return this;
    }

    public Task addStepById(String viewId, int action) {
        addStep(new Step(action, viewId));
        return this;
    }

    public Task addStep(String actionValue, int action, boolean extraMatch, boolean boundMatch) {
        addStep(new Step(actionValue, action, extraMatch, boundMatch));
        return this;
    }

    public Task addStep(String actionValue, int action, String viewId) {
        addStep(new Step(actionValue, action, viewId, true));
        return this;
    }

    public Task addStep(String actionValue, int action, Bundle bundle) {
        addStep(actionValue, action, bundle, null, true);
        return this;
    }

    public Task addStep(String actionValue, int action, boolean extraMatch) {
        addStep(actionValue, action, null, null, extraMatch);
        return this;
    }

    public Task addStep(String actionValue, int action, Uri uri) {
        addStep(actionValue, action, null, uri, true);
        return this;
    }

    public Task addStep(String actionValue, int action, Bundle bundle, Uri uri, boolean extraMatch) {
        addStep(new Step(actionValue, action, bundle, uri, extraMatch));
        return this;
    }

    public Queue<Step> getStepQueue() {
        return this.stepQueue;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public Task setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public Task setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

}
