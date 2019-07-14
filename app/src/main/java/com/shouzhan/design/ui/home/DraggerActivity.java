package com.shouzhan.design.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.fshows.android.stark.utils.FsLogUtil;
import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityDraggerBinding;
import com.shouzhan.design.model.javabean.Student;

import javax.inject.Inject;


/**
 * @author danbin
 * @version DraggerActivity.java, v 0.1 2019-02-27 上午12:11 danbin
 */
public class DraggerActivity extends BaseActivity<ActivityDraggerBinding> {

    private static final String TAG = DraggerActivity.class.getSimpleName();

    @Inject
    Student mStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dragger;
    }

    @Override
    public void getData() {
        FsLogUtil.error(TAG, mStudent.name);
    }

}
