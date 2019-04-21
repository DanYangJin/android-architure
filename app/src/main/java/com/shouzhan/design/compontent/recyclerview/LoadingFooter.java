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

    protected LoadState mState = LoadState.NORMAL;

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
        setState(LoadState.LOADING);
    }

    @Override
    public void onComplete() {
        setState(LoadState.NORMAL);
    }

    @Override
    public void onNoMore() {
        setState(LoadState.NOMORE);
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
    public void setState(LoadState status) {
        Log.e(TAG, "LoadingFooterStatus: old status: " + mState + " , " + status);
        if (mState == status) {
            return;
        }
        mState = status;
        switch (status) {
            case NORMAL:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(GONE);
                }
                break;
            case LOADING:
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                break;
            case NOMORE:
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.VISIBLE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                break;
            case NETERROR:
                break;
            default:
                break;
        }
    }

}