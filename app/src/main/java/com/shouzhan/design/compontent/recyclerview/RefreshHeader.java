package com.shouzhan.design.compontent.recyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shouzhan.design.R;


/**
 * @author danbin
 */
public class RefreshHeader extends RelativeLayout implements IRefreshHeader {

    private int mState = STATE_NORMAL;

    private RelativeLayout mRefreshHeaderView;
    private TextView mTitleView;

    public int mMeasuredHeight;

    public RefreshHeader(Context context) {
        this(context, null, -1);
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        mRefreshHeaderView = (RelativeLayout) inflate(getContext(), R.layout.layout_header_view, this);
        mRefreshHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.CENTER);
        mTitleView = findViewById(R.id.header_tv);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setState(int state) {
        if (state == mState) {
            return;
        }

        switch (state) {
            case STATE_NORMAL:
                break;
            case STATE_RELEASE_TO_REFRESH:
                break;
            case STATE_REFRESHING:
                mTitleView.setVisibility(View.VISIBLE);
                break;
            case STATE_DONE:
                break;
            default:
                break;
        }
        mState = state;
    }

    @Override
    public void onReset() {
        setState(STATE_NORMAL);
    }

    @Override
    public void onPrepare() {
        setState(STATE_RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshing() {
        setState(STATE_REFRESHING);
    }

    @Override
    public void onMove(float offSet, float sumOffSet) {
        int top = getTop();
        if (offSet > 0 && top == 0) {
            setVisibleHeight((int) offSet + getVisibleHeight());
        } else if (offSet < 0 && getVisibleHeight() > 0) {
            layout(getLeft(), 0, getRight(), getHeight());
            setVisibleHeight((int) offSet + getVisibleHeight());
        }
        if (mState <= STATE_RELEASE_TO_REFRESH) {
            if (getVisibleHeight() > mMeasuredHeight) {
                onPrepare();
            } else {
                onReset();
            }
        }
    }

    @Override
    public boolean onRelease() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) {
            isOnRefresh = false;
        }

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        if (mState == STATE_REFRESHING && height > mMeasuredHeight) {
            smoothScrollTo(mMeasuredHeight);
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }

        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        reset();
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mRefreshHeaderView.getLayoutParams();
        lp.height = height;
        mRefreshHeaderView.setLayoutParams(lp);
    }

    @Override
    public int getVisibleHeight() {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mRefreshHeaderView.getLayoutParams();
        return lp.height;
    }

    @Override
    public int getVisibleWidth() {
        return 0;
    }

    @Override
    public int getType() {
        return TYPE_HEADER_NORMAL;
    }

    public void reset() {
        smoothScrollTo(0);
        setState(STATE_NORMAL);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }


}