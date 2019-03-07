package com.shouzhan.design.compontent.recyclerview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.shouzhan.design.R;


/**
 * @author danbin
 */
public class LoadingFooter extends RelativeLayout implements ILoadMoreFooter {

    protected State mState = State.Normal;
    private String mNoMoreHint = null;

    public LoadingFooter(Context context) {
        super(context);
        initView();
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.layout_footer_view, this);
        setOnClickListener(null);
        onReset();
    }

    public void setViewBackgroundColor(int color) {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setNoMoreHint(String noMoreHint) {
        this.mNoMoreHint = noMoreHint;
    }

    public void setState(State status) {
        setState(status, true);
    }

    @Override
    public void onReset() {
        onComplete();
    }

    @Override
    public void onLoading() {
        setState(State.Loading);
    }

    @Override
    public void onComplete() {
        setState(State.Normal);
    }

    @Override
    public void onNoMore() {
        setState(State.NoMore);
    }

    @Override
    public View getFootView() {
        return this;
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;

        switch (status) {
            case Normal:
                break;
            case Loading:
                break;
            case NoMore:
                break;
            case NetWorkError:
                break;
            default:
                break;
        }
    }


    public enum State {
        Normal, NoMore, Loading, NetWorkError
    }
}