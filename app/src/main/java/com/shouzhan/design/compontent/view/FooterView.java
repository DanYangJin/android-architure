package com.shouzhan.design.compontent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.shouzhan.design.R;
import com.shouzhan.design.compontent.recyclerview.IBottomView;


/**
 * @author danbin
 * @version FooterView.java, v 0.1 2019-02-26 下午11:23 danbin
 */
public class FooterView extends RelativeLayout implements IBottomView {

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_footer_view, this, true);
        initUI();
    }

    private void initUI(){

    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {

    }


    @Override
    public void reset() {

    }

}
