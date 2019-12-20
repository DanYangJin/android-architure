package com.shouzhan.design.dialog;

/**
 * @author danbin
 * @version PriorityQueueInfo.java, v 0.1 2019-12-19 23:31 danbin
 */
public class PriorityQueueInfo {

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
        mDialogFragment = dialogFragment;
        mDialogTag = tag;
        mPriority = priority;
    }

    public BaseDialogFragment getDialogFragment() {
        return mDialogFragment;
    }

    public String getDialogTag() {
        return mDialogTag;
    }

    public int getPriority() {
        return mPriority;
    }


}
