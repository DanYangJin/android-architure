package com.shouzhan.design.dialog;

import androidx.fragment.app.FragmentManager;

import com.annimon.stream.Collectors;
import com.annimon.stream.ComparatorCompat;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author danbin
 * @version DialogManager.java, v 0.1 2019-12-19 21:39 danbin
 * 统一对话框管理
 */
public class DialogManager {

    private static Queue<PriorityQueueInfo> mPriorityQueue = new ConcurrentLinkedQueue<>();
    private static DialogManager mInstance;
    private FragmentManager mFragmentManager;

    public static DialogManager getInstance(FragmentManager fm) {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager(fm);
                }
            }
        }
        return mInstance;
    }

    private DialogManager(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    /**
     * 是否能显示下一个弹框
     *
     * @return
     */
    public boolean hasNextShowDialog() {
        return mPriorityQueue.size() < 2;
    }

    /**
     * 将对话框放入队列中
     */
    public void pushToQueue(PriorityQueueInfo dialogPriorityInfo) {
        BaseDialogFragment fragment = dialogPriorityInfo.getDialogFragment();
        if (fragment == null) {
            return;
        }
        mPriorityQueue.add(dialogPriorityInfo);
        fragment.setDismissListener(this::nextTask);
        if (hasNextShowDialog()) {
            startNextIf();
        }
    }

    /**
     * 提供外部下一个任务的方法,在弹窗消失时候调用
     */
    private void nextTask() {
        removeTopTask();
        startNextIf();
    }

    /**
     * 移除队列的头,获取最新队列头
     */
    private void removeTopTask() {
        mPriorityQueue.poll();
    }

    /**
     * 显示下一个弹窗任务
     */
    private void startNextIf() {
        if (mPriorityQueue == null || mPriorityQueue.isEmpty()) {
            return;
        }
        orderDialogQueue();
        PriorityQueueInfo mCurPriorityQueueInfo = mPriorityQueue.element();
        if (mCurPriorityQueueInfo == null) {
            return;
        }
        BaseDialogFragment fragment = mCurPriorityQueueInfo.getDialogFragment();
        if (fragment != null) {
            fragment.showDialog(mFragmentManager,
                    mCurPriorityQueueInfo.getDialogTag());
        }
    }

    private void orderDialogQueue() {
        try {
            List<PriorityQueueInfo> infoLists =  Stream.of(mPriorityQueue)
                    .sorted(ComparatorCompat.comparing(PriorityQueueInfo::getPriority).reversed())
                    .collect(Collectors.toList());
            mPriorityQueue.clear();
            Stream.of(infoLists).forEach(n -> mPriorityQueue.add(n));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
