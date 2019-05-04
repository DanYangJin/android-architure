package com.shouzhan.design.ui.home;

import android.util.Log;
import android.view.View;

import com.shouzhan.design.R;
import com.shouzhan.design.base.BaseActivity;
import com.shouzhan.design.databinding.ActivityRxBindingBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;


/**
 * @author danbin
 * @version RxBindingActivity.java, v 0.1 2019-05-04 上午10:08 danbin
 */
public class RxBindingActivity extends BaseActivity<ActivityRxBindingBinding> {

    private static final String TAG = RxBindingActivity.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.activity_rx_binding;
    }

    @Override
    public void initView() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(5);
        Flowable.fromIterable(list)
                .filter(integer -> integer > 3)
                .subscribe(integer -> {
                    System.out.println(integer);
                    Log.e(TAG, "accept: " + integer);
                });

    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View view) {

    }

}
