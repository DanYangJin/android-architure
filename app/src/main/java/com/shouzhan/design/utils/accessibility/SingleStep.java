package com.shouzhan.design.utils.accessibility;

import android.net.Uri;
import android.os.Bundle;

import java.io.Serializable;

/**
 * @author danbin
 * @version AccessibilityStep.java, v 0.1 2020-05-06 7:38 PM danbin
 * 辅助功能每一步
 */
public class SingleStep implements Serializable {

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

    public SingleStep(String actionValue, int action, Bundle bundle, Uri uri, boolean exactMatch) {
        this.actionValue = actionValue;
        this.params = bundle;
        this.data = uri;
        this.action = action;
        this.exactMatch = exactMatch;
    }

    public SingleStep(String viewId, int action) {
        this.action = action;
        this.viewId = viewId;
    }

    public SingleStep(String actionValue, int action, String viewId, boolean exactMatch) {
        this.actionValue = actionValue;
        this.action = action;
        this.viewId = viewId;
        this.exactMatch = exactMatch;
    }

    public SingleStep(String actionValue, int action, boolean exactMatch, boolean boundMatch) {
        this.actionValue = actionValue;
        this.action = action;
        this.exactMatch = exactMatch;
        this.boundMatch = boundMatch;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int i) {
        this.action = i;
    }

    public String getActionValue() {
        return this.actionValue;
    }

    public void setActionValue(String str) {
        this.actionValue = str;
    }

    public Uri getData() {
        return this.data;
    }

    public void setData(Uri uri) {
        this.data = uri;
    }

    public boolean isExactMatch() {
        return this.exactMatch;
    }

    public void setExactMatch(boolean z) {
        this.exactMatch = z;
    }

    public Bundle getParams() {
        return this.params;
    }

    public void setParams(Bundle bundle) {
        this.params = bundle;
    }

    public String getViewId() {
        return this.viewId;
    }

    public void setViewId(String str) {
        this.viewId = str;
    }

    public boolean isBoundMatch() {
        return this.boundMatch;
    }

    public void setBoundMatch(boolean z) {
        this.boundMatch = z;
    }


}
