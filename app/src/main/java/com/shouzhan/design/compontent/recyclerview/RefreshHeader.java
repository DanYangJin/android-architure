package com.shouzhan.design.compontent.recyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.shouzhan.design.R;

import javax.annotation.Nullable;

/**
 *
 * @author lyf
 * @date 2019/4/22
 */
public class RefreshHeader extends LinearLayout implements IRefreshHeader {

    private Context mContext;
    private int mState = STATE_NORMAL;
    private int mMeasuredHeight;
    private RelativeLayout mLoadingRl;
    private SimpleDraweeView mLoadingGif;

    public RefreshHeader(Context context) {
        this(context, null);
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_refresh_header, this);
        mLoadingRl = findViewById(R.id.gif_container);
        mLoadingGif = findViewById(R.id.loading_gif);
        initDraweeController();
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
        setGravity(Gravity.CENTER_HORIZONTAL);
        setVisibleHeight(0);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initDraweeController() {
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(false)
                .setUri(UriUtil.parseUriOrNull("res://" + mContext.getPackageName() + "/" + R.mipmap.refresh_header_iv))
                .setControllerListener(new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (animatable != null) {
                            animatable.start();
                        }
                    }
                })
                .build();
        mLoadingGif.setController(mDraweeController);
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
                break;
            case STATE_DONE:
                break;
            default:
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
        if (offSet > 0 && top == 0 ){
            setVisibleHeight((int) offSet + getVisibleHeight());
        }else if (offSet < 0 && getVisibleHeight() > 0){
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
        new Handler().postDelayed(() -> reset(), 500L);
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    public void reset() {
        smoothScrollTo(0);
        setState(STATE_NORMAL);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(valueAnimator -> setVisibleHeight((int) valueAnimator.getAnimatedValue()));
        animator.start();
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.height = height;
        mLoadingRl.setLayoutParams(lp);
    }

    @Override
    public int getVisibleHeight() {
        return mLoadingRl.getHeight();
    }

    @Override
    public int getVisibleWidth() {
        return 0;
    }

    @Override
    public int getRealMeasuredHeight() {
        return mMeasuredHeight;
    }

    @Override
    public int getType() {
        return TYPE_HEADER_NORMAL;
    }

}
