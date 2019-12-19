package com.shouzhan.design.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shouzhan.design.R;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author danbin
 * @version AlertDialogFragment.java, v 0.1 2019-12-19 22:13 danbin
 */
public class AlertDialogFragment extends BaseDialogFragment {

    private TextView mMsgTv;

    @Override
    protected int setContentView() {
        return R.layout.layout_alert_dialog;
    }

    @Override
    protected void initUi() {
        super.initUi();
        mMsgTv = mRootView.findViewById(R.id.alert_dialog_content);
        String msg = mBuilder.getDialogMsg();
        if (StringUtils.isEmpty(msg)) {
            if (mMsgTv.getText() == null) {
                mMsgTv.setText(R.string.common_msg);
            }
        } else {
            mMsgTv.setText(msg);
        }
    }

    @Override
    public void changeDialogSize() {
        Dialog dlg = getDialog();
        dlg.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
        params.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        dlg.getWindow().setGravity(Gravity.CENTER);
        dlg.getWindow().setAttributes(params);
    }

    @Override
    protected void fillData() {

    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBuilder.isCancelable()) {
                dismissDialog();
                return true;
            } else {
                return true;
            }
        }
        return false;
    }


}
