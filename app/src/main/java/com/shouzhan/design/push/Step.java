package com.shouzhan.design.push;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Keep;

import java.io.Serializable;

/**
 * @author danbin
 */
@Keep
public class Step implements Serializable {
    public static final int STEP_ACTION_BACK = 10;
    public static final int STEP_ACTION_CLICK = 1;
    public static final int STEP_ACTION_CLICK_ID = 14;
    public static final int STEP_ACTION_CLOSE = 3;
    public static final int STEP_ACTION_HOME = 11;
    public static final int STEP_ACTION_JUMP = 4;
    public static final int STEP_ACTION_OPEN = 2;
    public static final int STEP_ACTION_RECENTS = 12;
    public static final int STEP_ACTION_SCROLL_TOP = 5;
    public static final int STEP_ACTION_SLEEP = 6;
    public static final int STEP_ACTION_SLIDE_OPEN = 17;
    public static final int STEP_ACTION_TRY_CLICK = 7;
    public static final int STEP_ACTION_TRY_CLICK_ID = 15;
    public static final int STEP_ACTION_TRY_CLICK_JUMP = 13;
    public static final int STEP_ACTION_TRY_CLOSE = 9;
    public static final int STEP_ACTION_TRY_CLOSE_ID = 17;
    public static final int STEP_ACTION_TRY_OPEN = 8;
    public static final int STEP_ACTION_TRY_OPEN_ID = 16;

    private int action;
    private String actionValue;
    private boolean boundMatch;
    private Uri data;
    private boolean exactMatch;
    private Bundle params;
    private String viewId;

    public Step(String actionValue, int action, Bundle bundle, Uri uri, boolean exactMatch) {
        this.actionValue = actionValue;
        this.params = bundle;
        this.data = uri;
        this.action = action;
        this.exactMatch = exactMatch;
    }

    public Step(int action, String viewId) {
        this.action = action;
        this.viewId = viewId;
    }

    public Step(String actionValue, int action, String viewId, boolean exactMatch) {
        this.actionValue = actionValue;
        this.action = action;
        this.viewId = viewId;
        this.exactMatch = exactMatch;
    }

    public Step(String actionValue, int action, boolean exactMatch, boolean boundMatch) {
        this.actionValue = actionValue;
        this.action = action;
        this.exactMatch = exactMatch;
        this.boundMatch = boundMatch;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }

    public boolean isBoundMatch() {
        return boundMatch;
    }

    public void setBoundMatch(boolean boundMatch) {
        this.boundMatch = boundMatch;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public boolean isExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

    public Bundle getParams() {
        return params;
    }

    public void setParams(Bundle params) {
        this.params = params;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }
}
