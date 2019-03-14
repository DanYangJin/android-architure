package com.shouzhan.design.compontent.recyclerview;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


/**
 * @author danbin
 */
public class LuRecyclerView extends RecyclerView {

    private boolean mLoadMoreEnabled = true;
    private boolean mRefreshing = false;
    private boolean mLoadingData = false;
    private OnLoadMoreListener mLoadMoreListener;
    private OnScrollListener mLScrollListener;
    private ILoadMoreFooter mLoadMoreFooter;
    private View mEmptyView;
    private View mFootView;

    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();
    private int mPageSize = 10;

    private LuRecyclerViewAdapter mWrapAdapter;
    private boolean isNoMore = false;
    private boolean isCritical = false;
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
     * 触发在上下滑动监听器的容差距离
     */
    private static final int HIDE_THRESHOLD = 20;

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

    public LuRecyclerView(Context context) {
        this(context, null);
    }

    public LuRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        if (mLoadMoreEnabled) {
            setLoadMoreFooter(new LoadingFooter(getContext()), false);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mWrapAdapter != null && mDataObserver != null) {
            mWrapAdapter.getInnerAdapter().unregisterAdapterDataObserver(mDataObserver);
        }
        mWrapAdapter = (LuRecyclerViewAdapter) adapter;
        super.setAdapter(mWrapAdapter);
        mWrapAdapter.getInnerAdapter().registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
        if (mLoadMoreEnabled && mWrapAdapter.getFooterViewsCount() == 0) {
            mWrapAdapter.addFooterView(mFootView);
        }

    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter instanceof LRecyclerViewAdapter) {
                LRecyclerViewAdapter lRecyclerViewAdapter = (LRecyclerViewAdapter) adapter;
                if (lRecyclerViewAdapter.getInnerAdapter() != null && mEmptyView != null) {
                    int count = lRecyclerViewAdapter.getInnerAdapter().getItemCount();
                    if (count == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        LuRecyclerView.this.setVisibility(View.GONE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                        LuRecyclerView.this.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (adapter != null && mEmptyView != null) {
                    if (adapter.getItemCount() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        LuRecyclerView.this.setVisibility(View.GONE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                        LuRecyclerView.this.setVisibility(View.VISIBLE);
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

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    /**
     * set view when no content item
     *
     * @param emptyView visiable view when items is empty
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        this.mDataObserver.onChanged();
    }

    public void setRefreshing(boolean refreshing) {
        mRefreshing = refreshing;
    }

    /**
     * @param pageSize 一页加载的数量
     */
    public void refreshComplete(int pageSize) {
        this.mPageSize = pageSize;
        if (mRefreshing) {
            isNoMore = false;
            mRefreshing = false;
            if (mWrapAdapter.getInnerAdapter().getItemCount() < pageSize) {
                mFootView.setVisibility(GONE);
            }
        } else if (mLoadingData) {
            mLoadingData = false;
            mLoadMoreFooter.onComplete();
        }
        if (mWrapAdapter.getInnerAdapter().getItemCount() == mPageSize) {
            isCritical = true;
        } else {
            isCritical = false;
        }
    }

    /**
     * @param pageSize 一页加载的数量
     * @param total    总数
     */
    public void refreshComplete(int pageSize, int total) {
        this.mPageSize = pageSize;
        if (mRefreshing) {
            isNoMore = false;
            mRefreshing = false;
            if (mWrapAdapter.getInnerAdapter().getItemCount() < pageSize) {
                mFootView.setVisibility(GONE);
            }
        } else if (mLoadingData) {
            mLoadingData = false;
            mLoadMoreFooter.onComplete();
        }
        if (pageSize < total) {
            isNoMore = false;
        }
        //处理特殊情况 最后一行显示出来了加载更多的view的一部分
        if (mWrapAdapter.getInnerAdapter().getItemCount() == mPageSize) {
            isCritical = true;
        } else {
            isCritical = false;
        }
    }

    /**
     * 此方法主要是为了满足数据不满一屏幕或者数据小于pageSize的情况下，是否显示footerview
     * 在分页情况下使用refreshComplete(int pageSize, int total, boolean false)就相当于refreshComplete(int pageSize, int total)
     *
     * @param pageSize       一页加载的数量
     * @param total          总数
     * @param isShowFootView 是否需要显示footerview（前提条件是：getItemCount() < pageSize）
     */
    public void refreshComplete(int pageSize, int total, boolean isShowFootView) {
        this.mPageSize = pageSize;
        if (mRefreshing) {
            isNoMore = false;
            mRefreshing = false;
            if (isShowFootView) {
                mFootView.setVisibility(VISIBLE);
            } else {
                if (mWrapAdapter.getInnerAdapter().getItemCount() < pageSize) {
                    mFootView.setVisibility(GONE);
                    mWrapAdapter.removeFooterView();
                } else {
                    if (mWrapAdapter.getFooterViewsCount() == 0) {
                        mWrapAdapter.addFooterView(mFootView);
                    }
                }
            }
        } else if (mLoadingData) {
            mLoadingData = false;
            mLoadMoreFooter.onComplete();
        }
        if (pageSize < total) {
            isNoMore = false;
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
        mLoadingData = false;
        isNoMore = noMore;
        if (isNoMore) {
            mFootView.setVisibility(VISIBLE);
            mLoadMoreFooter.onNoMore();
        } else {
            mLoadMoreFooter.onComplete();
        }
    }

    /**
     * 设置自定义的footerview
     *
     * @param loadMoreFooter
     * @param isCustom       是否自定义footerview
     */
    public void setLoadMoreFooter(ILoadMoreFooter loadMoreFooter, boolean isCustom) {
        this.mLoadMoreFooter = loadMoreFooter;
        if (isCustom) {
            if (null != mWrapAdapter && mWrapAdapter.getFooterViewsCount() > 0) {
                mWrapAdapter.removeFooterView();
            }
        }
        mFootView = loadMoreFooter.getFootView();
        mFootView.setVisibility(VISIBLE);

        //wxm:mFootView inflate的时候没有以RecyclerView为parent，所以要设置LayoutParams
        ViewGroup.LayoutParams layoutParams = mFootView.getLayoutParams();
        if (layoutParams != null) {
            mFootView.setLayoutParams(new LayoutParams(layoutParams));
        } else {
            mFootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        if (isCustom) {
            if (mLoadMoreEnabled && mWrapAdapter.getFooterViewsCount() == 0) {
                mWrapAdapter.addFooterView(mFootView);
            }
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

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public void setLScrollListener(OnScrollListener listener) {
        mLScrollListener = listener;
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
        if (null != mLScrollListener) {
            mLScrollListener.onScrolled(mScrolledXDistance, mScrolledYDistance);
        }
        if (mLoadMoreEnabled) {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0
                    && lastVisibleItemPosition >= totalItemCount - 1
                    && (isCritical ? totalItemCount >= visibleItemCount : totalItemCount > visibleItemCount)
                    && !isNoMore
                    && !mRefreshing) {
                isCanLoadingData = true;
                mFootView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (mLScrollListener != null) {
            mLScrollListener.onScrollStateChanged(state);
        }
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (mLoadMoreListener != null && isCanLoadingData && !mLoadingData) {
                mLoadingData = true;
                mLoadMoreFooter.onLoading();
                mLoadMoreListener.onLoadMore();
                isCanLoadingData = false;
            }
        }
    }

    /**
     * 计算当前是向上滑动还是向下滑动
     */
    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (null != mLScrollListener) {
            if (firstVisibleItemPosition == 0) {
                if (!mIsScrollDown) {
                    mIsScrollDown = true;
                    mLScrollListener.onScrollDown();
                }
            } else {
                if (mDistance > HIDE_THRESHOLD && mIsScrollDown) {
                    mIsScrollDown = false;
                    mLScrollListener.onScrollUp();
                    mDistance = 0;
                } else if (mDistance < -HIDE_THRESHOLD && !mIsScrollDown) {
                    mIsScrollDown = true;
                    mLScrollListener.onScrollDown();
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
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
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
