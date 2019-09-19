package com.shouzhan.design.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.fshows.android.stark.utils.UiHandlerUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityScreenAdapterBinding;
import com.shouzhan.design.utils.Util;


/**
 * @author danbin
 * @version ScreenAdapterActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
public class ScreenAdapterActivity extends BaseActivity<ActivityScreenAdapterBinding> {

    private static final String TAG = ScreenAdapterActivity.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_adapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 打印设备信息
        Util.printDeviceInfo(this);
        // 打印布局宽高, 以dp为单位
        Util.dumpLayoutParams(mBinding.testViewDp.getLayoutParams());
        // 打印布局宽高, 以mm为单位
        Util.dumpLayoutParams(mBinding.testViewMm.getLayoutParams());
        // 打印tv的字体大小
        Util.dumpTextSize(mBinding.testTvSizeSp);
        Util.formatNanoTime();
        Util.convertDpi();
        // 打印iv的宽高
        UiHandlerUtil.runOnUiThread(() -> {
            Log.e(TAG, "width: " + mBinding.testIv.getWidth());
            Log.e(TAG, "height: " + mBinding.testIv.getHeight());
        }, 5000);
        getData();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getData() {

    }


}
