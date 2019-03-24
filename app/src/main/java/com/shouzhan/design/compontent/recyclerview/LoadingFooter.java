package com.shouzhan.design.compontent.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.shouzhan.design.R;


/**
 * @author danbin
 */
public class LoadingFooter extends RelativeLayout implements ILoadMoreFooter {

    private static final String TAG = LoadingFooter.class.getSimpleName();

    protected State mState = State.Normal;

    private View mTheEndView;
    private View mLoadingView;

    public LoadingFooter(Context context) {
        this(context, null, -1);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.layout_footer_view, this);
        mLoadingView = findViewById(R.id.footer_loading_view);
        mTheEndView = findViewById(R.id.no_more_tv);
        setOnClickListener(null);
        onReset();
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
     * 设置状态底部状态
     *
     * @param status
     */
    public void setState(State status) {
        Log.e(TAG, "LoadingFooterStatus: old status: " + mState + " , " + status);
        if (mState == status) {
            return;
        }
        mState = status;
        switch (status) {
            case Normal:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }
                break;
            case Loading:
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                break;
            case NoMore:
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.VISIBLE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
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