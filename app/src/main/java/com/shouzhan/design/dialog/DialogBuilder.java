package com.shouzhan.design.dialog;

import android.content.Context;
import androidx.annotation.StyleRes;

import com.shouzhan.design.R;

/**
 * @author danbin
 * @version DialogBuilder.java, v 0.1 2019-12-19 21:20 danbin
 */
public class DialogBuilder {

    /**
     * 上下文
     * */
    private Context mContext;
    /**
     * 对话框标题
     * */
    private String mDialogTitle;
    /**
     * 对话框内容
     * */
    private String mDialogMsg;
    /**
     * 确定按钮
     * */
    private String mDialogConfirm;
    /**
     * 取消按钮
     * */
    private String mDialogCancel;
    /**
     * 是否可取消
     * */
    private boolean isCancelable = false;
    /**
     * 是否显示标题
     * */
    private boolean isShowTitle = true;
    /**
     * 是否显示取消按钮
     * */
    private boolean isShowCancelButton = true;

    /**
     * 对话框样式
     * */
    @StyleRes
    private int mDialogStyleRes = R.style.CommonDialogFragmentStyleTheme;

    public static DialogBuilder builder(Context mContext) {
        return new DialogBuilder(mContext);
    }

    public DialogBuilder(Context mContext) {
        this.mContext = mContext;
    }

    public DialogBuilder setDialogTitle(int res) {
        return setDialogTitle(mContext.getString(res));
    }

    public DialogBuilder setDialogTitle(String title) {
        this.mDialogTitle = title;
        return this;
    }

    public DialogBuilder setDialogMsg(int res) {
        return setDialogMsg(mContext.getString(res));
    }

    public DialogBuilder setDialogMsg(String msg) {
        this.mDialogMsg = msg;
        return this;
    }

    public DialogBuilder setDialogConfirm(int res) {
        return setDialogConfirm(mContext.getString(res));
    }

    public DialogBuilder setDialogConfirm(String confirm) {
        this.mDialogConfirm = confirm;
        return this;
    }

    public DialogBuilder setDialogCancel(int res) {
        return setDialogCancel(mContext.getString(res));
    }

    public DialogBuilder setDialogCancel(String cancel) {
        this.mDialogCancel = cancel;
        return this;
    }

    public DialogBuilder setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        return this;
    }

    public DialogBuilder setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
        return this;
    }

    public DialogBuilder setDialogStyleRes(@StyleRes int styleRes) {
        this.mDialogStyleRes = styleRes;
        return this;
    }

    public DialogBuilder setShowCancelButton(boolean isShowCancelButton) {
        this.isShowCancelButton = isShowCancelButton;
        return this;
    }

    public String getDialogTitle() {
        return mDialogTitle;
    }

    public String getDialogMsg() {
        return mDialogMsg;
    }

    public String getDialogConfirm() {
        return mDialogConfirm;
    }

    public String getDialogCancel() {
        return mDialogCancel;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public boolean isShowTitle() {
        return isShowTitle;
    }

    public boolean isShowCancelButton() {
        return isShowCancelButton;
    }

    public int getDialogStyleRes() {
        return mDialogStyleRes;
    }

}
