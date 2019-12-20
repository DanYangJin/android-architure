package com.shouzhan.design.dialog;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.TextView;

import com.shouzhan.design.R;

import org.apache.commons.lang3.StringUtils;

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
