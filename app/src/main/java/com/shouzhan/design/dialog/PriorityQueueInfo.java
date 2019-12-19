package com.shouzhan.design.dialog;

import android.util.Log;

/**
 * @author danbin
 * @version PriorityQueueInfo.java, v 0.1 2019-12-19 23:31 danbin
 */
public class PriorityQueueInfo implements Comparable<PriorityQueueInfo> {

    /**
     * 对话框显示优先级
     */
    private int mPriority;

    /**
     * 对话框
     */
    private BaseDialogFragment mDialogFragment;

    /**
     * 对话框tag
     */
    private String mDialogTag;

    public PriorityQueueInfo(BaseDialogFragment dialogFragment, String tag) {
        this(Priority.PRIORITY_NORMAL, dialogFragment, tag);
    }

    public PriorityQueueInfo(int priority, BaseDialogFragment dialogFragment, String tag) {
        Log.e("Catch", "tag: " + tag);
        mDialogFragment = dialogFragment;
        mPriority = priority;
        mDialogTag = tag;
    }

    public void setPriority(int priority) {
        this.mPriority = priority;
    }

    public int getPriority() {
        return mPriority;
    }

    public BaseDialogFragment getDialogFragment() {
        return mDialogFragment;
    }

    public void setDialogFragment(BaseDialogFragment dialogFragment) {
        mDialogFragment = dialogFragment;
    }

    public String getDialogTag() {
        return mDialogTag;
    }

    public void setDialogTag(String dialogTag) {
        mDialogTag = dialogTag;
    }

    @Override
    public int compareTo(PriorityQueueInfo o) {
        if (this.mPriority > o.getPriority()) {
            return -1;
        } else if (this.mPriority < o.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }

}
