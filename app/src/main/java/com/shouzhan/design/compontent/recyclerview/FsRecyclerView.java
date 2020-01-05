package com.shouzhan.design.compontent.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;


/**
 * @author danbin
 */
public class FsRecyclerView extends RecyclerView {

    /**
     * 触发在上下滑动监听器的容差距离
     */
    private static final int HIDE_THRESHOLD = 20;
    /**
     * 默认每页数量
     */
    private static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 拖动速度
     */
    private static final float DRAG_RATE = 2.0f;

    private boolean mPullRefreshEnabled = true;
    private boolean mRefreshingData = false;

    private boolean mLoadMoreEnabled = true;
    private boolean mLoadingData = false;

    private OnLoadRefreshListener mLoadRefreshListener;
    private OnScrollListener mScrollListener;
    private IRefreshHeader mRefreshHeader;
    private ILoadMoreFooter mLoadMoreFooter;
    /**
     * 设置空视图
     */
    private View mEmptyView = null;
    /**
     * 设置底部上拉加载布局
     */
    private View mFootView = null;
    /**
     * 设置顶部上拉加载布局
     */
    private View mHeadView = null;

    /**
     * 设置错误布局
     */
    private View mErrorView = null;
    /**
     * 是否显示错误布局
     */
    private boolean mErrorEnabled = false;

    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();
    private int mActivePointerId;
    private float mLastY = -1;
    private float sumOffSet;
    private int mPageSize = DEFAULT_PAGE_SIZE;

    private FsRecyclerViewAdapter mWrapAdapter;

    private boolean mIsDrag;
    private float startY;
    private float startX;

    private boolean isNoMore = false;
    /**
     * 处理临界情况
     */
    private boolean isCritical = false;

    /**
     * 是否能够加载数据
     */
    private boolean isCanLoadingData = false;
    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 滑动的距离
     */
    private int mDistance = 0;

    /**
     * 是否需要监听控制
     */
    private boolean mIsScrollDown = true;

    /**
     * Y轴移动的实际距离（最顶部为0）
     */
    private int mScrolledYDistance = 0;

    /**
     * X轴移动的实际距离（最左侧为0）
     */
    private int mScrolledXDistance = 0;

    private AppBarStateChangeListener.State appbarState = AppBarStateChangeListener.State.EXPANDED;

    public FsRecyclerView(Context context) {
        this(context, null);
    }

    public FsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FsRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        if (mPullRefreshEnabled) {
            if (mHeadView != null) {
                if (mHeadView instanceof IRefreshHeader) {
                    setRefreshHeader((IRefreshHeader) mHeadView);
                }
            } else {
                setRefreshHeader(new RefreshHeader(getContext()));
            }
        }
        if (mLoadMoreEnabled) {
            if (mFootView != null) {
                if (mFootView instanceof ILoadMoreFooter) {
                    setLoadMoreFooter((ILoadMoreFooter) mFootView);
                }
            } else {
                setLoadMoreFooter(new LoadingFooter(getContext()));
            }
        }

    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mWrapAdapter != null && mDataObserver != null) {
            mWrapAdapter.getInnerAdapter().unregisterAdapterDataObserver(mDataObserver);
        }
        mWrapAdapter = (FsRecyclerViewAdapter) adapter;
        super.setAdapter(mWrapAdapter);
        mWrapAdapter.getInnerAdapter().registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
        if (mPullRefreshEnabled && mWrapAdapter.getHeaderViewsCount() == 0) {
            mWrapAdapter.addHeaderView(mHeadView);
        }
        if (mLoadMoreEnabled && mWrapAdapter.getFooterViewsCount() == 0) {
            mWrapAdapter.addFooterView(mFootView);
        }
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (mErrorEnabled && mErrorView != null) {
                showErrorView();
            } else {
                if (adapter instanceof FsRecyclerViewAdapter) {
                    FsRecyclerViewAdapter lRecyclerViewAdapter = (FsRecyclerViewAdapter) adapter;
                    if (lRecyclerViewAdapter.getInnerAdapter() != null && mEmptyView != null) {
                        int count = lRecyclerViewAdapter.getInnerAdapter().getItemCount();
                        if (count == 0) {
                            showEmptyView();
                        } else {
                            showFsRecyclerView();
                        }
                    }
                } else {
                    if (adapter != null && mEmptyView != null) {
                        if (adapter.getItemCount() == 0) {
                            showEmptyView();
                        } else {
                            showFsRecyclerView();
                        }
                    }
                }
            }
            if (mWrapAdapter != null) {
                mWrapAdapter.notifyDataSetChanged();
                if (mWrapAdapter.getInnerAdapter().getItemCount() < mPageSize) {
                    mFootView.setVisibility(GONE);
                }
            }

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart + mWrapAdapter.getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart + mWrapAdapter.getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart + mWrapAdapter.getHeaderViewsCount(), itemCount);
            if (mWrapAdapter.getInnerAdapter().getItemCount() < mPageSize) {
                mFootView.setVisibility(GONE);
            }

        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            int headerViewsCountCount = mWrapAdapter.getHeaderViewsCount();
            mWrapAdapter.notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }

    }

    /**
     * 解决嵌套RecyclerView滑动冲突问题
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                mIsDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsDrag) {
                    return false;
                }
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > 0 && distanceX > distanceY) {
                    mIsDrag = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsDrag = false;
                break;
            default:
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getY();
            mActivePointerId = ev.getPointerId(0);
            sumOffSet = 0;
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                sumOffSet = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int index = ev.getActionIndex();
                mActivePointerId = ev.getPointerId(index);
                mLastY = (int) ev.getY(index);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex == -1) {
                    pointerIndex = 0;
                    mActivePointerId = ev.getPointerId(pointerIndex);
                }
                final int moveY = (int) ev.getY(pointerIndex);
                final float deltaY = (moveY - mLastY) / DRAG_RATE;
                mLastY = moveY;
                sumOffSet += deltaY;
                if (isOnTop() && mPullRefreshEnabled && !mRefreshingData && (appbarState == AppBarStateChangeListener.State.EXPANDED)) {
                    if (mRefreshHeader.getType() == IRefreshHeader.TYPE_HEADER_NORMAL) {
                        mRefreshHeader.onMove(deltaY, sumOffSet);
                    } else if (mRefreshHeader.getType() == IRefreshHeader.TYPE_HEADER_MATERIAL) {
                        if (deltaY > 0 && !canScrollVertically(-1) || deltaY < 0 && !canScrollVertically(1)) {
                            //判断无法下拉和无法上拉（item过少的情况）
                            overScrollBy(0, (int) -deltaY, 0, 0, 0, 0, 0, (int) sumOffSet, true);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = -1;
                mActivePointerId = -1;
                if (isOnTop() && mPullRefreshEnabled && !mRefreshingData) {
                    if (mRefreshHeader != null && mRefreshHeader.onRelease()) {
                        if (mLoadRefreshListener != null) {
                            mRefreshingData = true;
                            mFootView.setVisibility(GONE);
                            mLoadRefreshListener.onRefresh();
                        }
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY != 0 && isTouchEvent) {
            mRefreshHeader.onMove(deltaY, sumOffSet);
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public boolean isOnTop() {
        return mPullRefreshEnabled && (mRefreshHeader.getHeaderView().getParent() != null);
    }

    public void setRefreshing(boolean refreshing) {
        mRefreshingData = refreshing;
    }

    public void refreshComplete() {
        if (mRefreshingData) {
            isNoMore = false;
            mRefreshingData = false;
            mRefreshHeader.refreshComplete();
            if (mWrapAdapter.getInnerAdapter().getItemCount() < mPageSize) {
                mFootView.setVisibility(View.GONE);
            } else {
                mLoadMoreFooter.onComplete();
            }
        } else if (mLoadingData) {
            mLoadingData = false;
            mLoadMoreFooter.onComplete();
        }
        //处理特殊情况 最后一行显示出来了加载更多的view的一部分
        if (mWrapAdapter.getInnerAdapter().getItemCount() == mPageSize) {
            isCritical = true;
        } else {
            isCritical = false;
        }
    }

    /**
     * 设置是否已加载全部
     *
     * @param noMore
     */
    public void setNoMore(boolean noMore) {
        isNoMore = noMore;
        mLoadingData = false;
        if (isNoMore) {
            mFootView.setVisibility(VISIBLE);
            mLoadMoreFooter.onNoMore();
        } else {
            mLoadMoreFooter.onComplete();
        }
    }

    /**
     * 设置自定义的headerview
     *
     * @param refreshHeader
     */
    public void setRefreshHeader(IRefreshHeader refreshHeader) {
        this.mRefreshHeader = refreshHeader;
        if (null != mWrapAdapter && mWrapAdapter.getHeaderViewsCount() > 0) {
            mWrapAdapter.removeHeaderView();
        }
        mHeadView = mRefreshHeader.getHeaderView();
        mHeadView.setVisibility(VISIBLE);

        //wxm:mHeaderView inflate的时候没有以RecyclerView为parent，所以要设置LayoutParams
        ViewGroup.LayoutParams layoutParams = mHeadView.getLayoutParams();
        if (layoutParams != null) {
            mHeadView.setLayoutParams(new LayoutParams(layoutParams));
        } else {
            mHeadView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * 下拉刷新是否可用
     */
    public void setRefreshEnabled(boolean enabled) {
        if (mWrapAdapter == null) {
            throw new NullPointerException("mWrapAdapter cannot be null, please make sure the variable mWrapAdapter have been initialized.");
        }
        mPullRefreshEnabled = enabled;
        if (enabled) {
            return;
        }
        mWrapAdapter.removeHeaderView();
    }

    /**
     * 设置自定义的footerview
     *
     * @param loadMoreFooter
     */
    public void setLoadMoreFooter(ILoadMoreFooter loadMoreFooter) {
        this.mLoadMoreFooter = loadMoreFooter;
        if (null != mWrapAdapter && mWrapAdapter.getFooterViewsCount() > 0) {
            mWrapAdapter.removeFooterView();
        }
        mFootView = loadMoreFooter.getFootView();
        mFootView.setVisibility(VISIBLE);

        //wxm:mFootView inflate的时候没有以RecyclerView为parent，所以要设置LayoutParams
        ViewGroup.LayoutParams layoutParams = mFootView.getLayoutParams();
        if (layoutParams != null) {
            mFootView.setLayoutParams(new LayoutParams(layoutParams));
        } else {
            mFootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * 到底加载是否可用
     */
    public void setLoadMoreEnabled(boolean enabled) {
        if (mWrapAdapter == null) {
            throw new NullPointerException("mWrapAdapter cannot be null, please make sure the variable mWrapAdapter have been initialized.");
        }
        mLoadMoreEnabled = enabled;
        if (enabled) {
            return;
        }
        mWrapAdapter.removeFooterView();
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    private void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        setVisibility(View.GONE);
    }

    public void seErrorEnabled(boolean enabled) {
        this.mErrorEnabled = enabled;
        this.mDataObserver.onChanged();
    }

    public void setErrorView(View errorView) {
        this.mErrorView = errorView;
    }

    private void showErrorView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.VISIBLE);
        }
        setVisibility(View.GONE);
    }

    private void showFsRecyclerView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
        setVisibility(View.VISIBLE);
    }

    public void setOnLoadRefreshListener(OnLoadRefreshListener listener) {
        mLoadRefreshListener = listener;
    }

    public void setScrollListener(OnScrollListener listener) {
        mScrollListener = listener;
    }

    public interface OnScrollListener {
        /**
         * RecyclerView向上滑动的监听事件
         */
        void onScrollUp();

        /**
         * RecyclerView向下滑动的监听事件
         */
        void onScrollDown();

        /**
         * RecyclerView正在滚动的监听事件
         *
         * @param distanceX
         * @param distanceY
         */
        void onScrolled(int distanceX, int distanceY);

        /**
         * RecyclerView正在滚动的监听事件
         *
         * @param state
         */
        void onScrollStateChanged(int state);
    }

    public void forceToRefresh() {
        if (mRefreshHeader.getVisibleHeight() > 0 || mRefreshingData || mLoadingData) {
            return;
        }
        if (mPullRefreshEnabled && mLoadRefreshListener != null) {
            mRefreshHeader.onRefreshing();
            int offSet = mRefreshHeader.getRealMeasuredHeight();
            mRefreshHeader.onMove(offSet, offSet);
            mRefreshingData = true;

            mFootView.setVisibility(GONE);
            mLoadRefreshListener.onRefresh();
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        int firstVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }
        switch (layoutManagerType) {
            case LinearLayout:
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(lastPositions);
                firstVisibleItemPosition = findMax(lastPositions);
                break;
            default:
                break;
        }

        calculateScrollUpOrDown(firstVisibleItemPosition, dy);
        mScrolledXDistance += dx;
        mScrolledYDistance += dy;
        mScrolledXDistance = (mScrolledXDistance < 0) ? 0 : mScrolledXDistance;
        mScrolledYDistance = (mScrolledYDistance < 0) ? 0 : mScrolledYDistance;
        if (mIsScrollDown && (dy == 0)) {
            mScrolledYDistance = 0;
        }
        if (null != mScrollListener) {
            mScrollListener.onScrolled(mScrolledXDistance, mScrolledYDistance);
        }
        if (mLoadMoreEnabled) {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0
                    && lastVisibleItemPosition >= totalItemCount - 1
                    && isCritical ? totalItemCount >= visibleItemCount : totalItemCount > visibleItemCount
                    && !isNoMore
                    && !mRefreshingData) {
                mFootView.setVisibility(View.VISIBLE);
                isCanLoadingData = true;
            }
        }
        if (isOnTop() && dy > 0 && mRefreshHeader.getType() == IRefreshHeader.TYPE_HEADER_MATERIAL && !mRefreshingData && (appbarState
                == AppBarStateChangeListener.State.EXPANDED)) {
            mRefreshHeader.onMove(dy, mScrolledYDistance);
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(state);
        }
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (mLoadRefreshListener != null && isCanLoadingData && !mLoadingData && !mRefreshingData) {
                mLoadingData = true;
                mLoadMoreFooter.onLoading();
                mLoadRefreshListener.onLoadMore();
                isCanLoadingData = false;
            }
        }
    }

    /**
     * 计算当前是向上滑动还是向下滑动
     */
    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (null != mScrollListener) {
            if (firstVisibleItemPosition == 0) {
                if (!mIsScrollDown) {
                    mIsScrollDown = true;
                    mScrollListener.onScrollDown();
                }
            } else {
                if (mDistance > HIDE_THRESHOLD && mIsScrollDown) {
                    mIsScrollDown = false;
                    mScrollListener.onScrollUp();
                    mDistance = 0;
                } else if (mDistance < -HIDE_THRESHOLD && !mIsScrollDown) {
                    mIsScrollDown = true;
                    mScrollListener.onScrollDown();
                    mDistance = 0;
                }
            }
        }
        boolean isScrollUp = (mIsScrollDown && dy > 0) || (!mIsScrollDown && dy < 0);
        if (isScrollUp) {
            mDistance += dy;
        }
    }

    public enum LayoutManagerType {
        /**
         * LayoutManager类型
         */
        LinearLayout, StaggeredGridLayout, GridLayout
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决LRecyclerView与CollapsingToolbarLayout滑动冲突的问题
        AppBarLayout appBarLayout = null;
        ViewParent p = getParent();
        while (p != null) {
            if (p instanceof CoordinatorLayout) {
                break;
            }
            p = p.getParent();
        }
        if (p instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) p;
            final int childCount = coordinatorLayout.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                final View child = coordinatorLayout.getChildAt(i);
                if (child instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout) child;
                    break;
                }
            }
            if (appBarLayout != null) {
                appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                    @Override
                    public void onStateChanged(AppBarLayout appBarLayout, State state) {
                    }
                });
            }
        }
    }

}
