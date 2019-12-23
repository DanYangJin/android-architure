package com.shouzhan.design.dialog;

import android.app.Dialog;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shouzhan.design.R;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author danbin
 * @version BaseDialogFragment.java, v 0.1 2019-12-19 22:13 danbin
 * 对话框基类,所有对话框继承该类
 */
public abstract class BaseDialogFragment extends DialogFragment implements OnKeyListener, View.OnClickListener {

    private static final String TAG = BaseDialogFragment.class.getSimpleName();

    /**
     * 初始化视图
     * */
    protected View mRootView;
    protected View mDialogLayout;
    protected TextView mTitleTv;
    protected View mContentLayout;
    protected View mBtnLayout;
    protected Button mConfirmBtn;
    protected View mBtnDriver;
    protected Button mCancelBtn;

    /**
     * 对话框构造者
     * */
    protected DialogBuilder mBuilder;
    /**
     * 对话框dismiss回调
     * */
    private DialogDismissListener mDismissListener;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "@@@@onCreateView");
        if (mRootView == null) {
            mRootView = inflater.inflate(setContentView(), container, false);
            initUi();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        fillData();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        changeDialogSize();
    }


    /**
     * 设置屏幕大小
     */
    public void changeDialogSize() {
        Dialog dlg = getDialog();
        Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        dlg.getWindow().setGravity(Gravity.CENTER);
        dlg.getWindow().setAttributes(params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            changeDialogSize();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e(TAG, "@@@@onCreateDialog");
        if (mBuilder == null) {
            mBuilder = DialogBuilder.builder(getContext());
        }
        Dialog dlg = new Dialog(Objects.requireNonNull(getActivity()), mBuilder.getDialogStyleRes());
        dlg.setCancelable(mBuilder.isCancelable());
        dlg.setOnKeyListener(this);
        return dlg;
    }

    public void setBuilder(DialogBuilder builder) {
        this.mBuilder = builder;
    }

    public DialogBuilder getBuilder() {
        return mBuilder;
    }

    public void setDismissListener(DialogDismissListener dismissListener) {
        mDismissListener = dismissListener;
    }

    /**
     * 初始化控件
     */
    protected void initUi() {
        mDialogLayout = mRootView.findViewById(R.id.common_dialog_layout);
        mTitleTv = mRootView.findViewById(R.id.common_dialog_title);
        String title = mBuilder.getDialogTitle();
        if (StringUtils.isEmpty(title)) {
            if (mTitleTv.getText() == null) {
                mTitleTv.setText(getString(R.string.common_title));
            }
        } else {
            mTitleTv.setText(title);
        }
        mContentLayout = mRootView.findViewById(R.id.common_dialog_content_layout);
        mBtnLayout = mRootView.findViewById(R.id.common_dialog_btn_layout);
        mBtnDriver = mRootView.findViewById(R.id.divider_line);
        mCancelBtn = mRootView.findViewById(R.id.cancel_btn);
        mCancelBtn.setOnClickListener(this);
        mCancelBtn.setVisibility(mBuilder.isShowCancelButton()? View.VISIBLE: View.GONE);
        mConfirmBtn = mRootView.findViewById(R.id.confirm_btn);
        mConfirmBtn.setOnClickListener(this);
        String cancel = mBuilder.getDialogCancel();
        String confirm = mBuilder.getDialogConfirm();
        if (StringUtils.isEmpty(confirm) && StringUtils.isEmpty(cancel)) {
            mBtnDriver.setVisibility(View.VISIBLE);
            mCancelBtn.setVisibility(View.VISIBLE);
            if (mCancelBtn.getText() == null) {
                mCancelBtn.setText(getString(R.string.common_cancel));
            }
            mConfirmBtn.setVisibility(View.VISIBLE);
            if (mConfirmBtn.getText() == null) {
                mConfirmBtn.setText(getString(R.string.common_confirm));
            }
        } else {
            if (StringUtils.isNotEmpty(confirm) && StringUtils.isNotEmpty(cancel)) {
                mBtnDriver.setVisibility(View.VISIBLE);
                mConfirmBtn.setVisibility(View.VISIBLE);
                mConfirmBtn.setText(confirm);
                mCancelBtn.setVisibility(View.VISIBLE);
                mCancelBtn.setText(cancel);
                return;
            }
            if (StringUtils.isEmpty(confirm)) {
                mConfirmBtn.setVisibility(View.GONE);
            } else {
                mConfirmBtn.setVisibility(View.VISIBLE);
                mConfirmBtn.setText(confirm);
                mBtnDriver.setVisibility(View.GONE);
            }
            if (StringUtils.isEmpty(cancel)) {
                mCancelBtn.setVisibility(View.GONE);
            } else {
                mCancelBtn.setVisibility(View.VISIBLE);
                mCancelBtn.setText(cancel);
                mBtnDriver.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "@@@onClick");
        dismissDialog();
    }

    protected boolean isDeActived() {
        return !isAdded() || isDetached();
    }

    public void dismissDialog() {
        if (isDeActived()) {
            return;
        }
        if (mDismissListener != null) {
            mDismissListener.onDissmiss();
        }
        dismiss();
    }


    /**
     * 显示dialogFragment
     * */
    public void showDialog(FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(this, tag);
        transaction.commitAllowingStateLoss();
        transaction.show(this);
    }

    /**
     * 对话框布局
     *
     * @return int
     */
    protected abstract int setContentView();

    /**
     * 填充数据
     */
    protected abstract void fillData();

}
