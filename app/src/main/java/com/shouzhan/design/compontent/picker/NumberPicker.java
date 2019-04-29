package com.shouzhan.design.compontent.picker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shouzhan.design.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danbin
 * @version NumberPicker.java, v 0.1 2019-04-29 上午3:20 danbin
 */
public class NumberPicker extends ListView {

    public static final String TAG = NumberPicker.class.getSimpleName();

    private static final int COLOR_DIVIDER_DEFAULT = Color.parseColor("#E5E5E5");
    private static final int COLOR_SOLID_DEFAULT = Color.parseColor("#FFFFFF");
    private static final int COLOR_SOLID_SELECT_DEFAULT = Color.parseColor("#F9F9F9");
    private static final int COLOR_SOLID_LABEL_DEFAULT = Color.parseColor("#999999");
    private static final int COLOR_SOLID_SELECT_LABEL_DEFAULT = Color.parseColor("#F04144");

    private static final int HEIGHT_DIVIDER_DEFAULT = 1;
    private static final int WHEEL_SIZE_DEFAULT = 4;
    private static final int CRITICAL_SIZE_DEFAULT = 3;
    private static final int EVEN_VALUE = 2;

    private Handler mHandler;

    private CycleWheelViewAdapter mAdapter;

    /**
     * Labels
     */
    private List<Integer> mLabels;

    /**
     * Color Of Selected Label
     */
    private int mLabelSelectColor = COLOR_SOLID_SELECT_LABEL_DEFAULT;

    /**
     * Color Of Unselected Label
     */
    private int mLabelColor = COLOR_SOLID_LABEL_DEFAULT;

    /**
     * Gradual Alpha
     */
    private float mAlphaGradual = 0.7f;

    /**
     * Color Of Divider
     */
    private int dividerColor = COLOR_DIVIDER_DEFAULT;

    /**
     * Height Of Divider
     */
    private int dividerHeight = HEIGHT_DIVIDER_DEFAULT;

    /**
     * Color of Selected Solid
     */
    private int selectedSolidColor = COLOR_SOLID_SELECT_DEFAULT;

    /**
     * Color of Unselected Solid
     */
    private int solidColor = COLOR_SOLID_DEFAULT;

    /**
     * Size Of Wheel , it should be odd number like 3 or greater
     */
    private int mWheelSize = WHEEL_SIZE_DEFAULT;

    /**
     * res Id of Wheel Item Layout
     */
    private int mItemLayoutId;

    /**
     * res Id of Label TextView
     */
    private int mItemLabelTvId;

    /**
     * Height of Wheel Item
     */
    private int mItemHeight;

    private int mCurrentPosition;

    private WheelItemSelectedListener mItemSelectedListener;

    public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public NumberPicker(Context context) {
        super(context);
    }

    private void initUI() {
        mHandler = new Handler();
        mItemLayoutId = R.layout.item_label;
        mItemLabelTvId = R.id.item_label_tv;
        mAdapter = new CycleWheelViewAdapter();
        setVerticalScrollBarEnabled(false);
        setScrollingCacheEnabled(false);
        setCacheColorHint(Color.TRANSPARENT);
        setFadingEdgeLength(0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setDividerHeight(0);
        setAdapter(mAdapter);
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    View itemView = getChildAt(0);
                    if (itemView == null) {
                        return;
                    }
                    float deltaY = itemView.getY();
                    if (deltaY == 0) {
                        return;
                    }
                    if (Math.abs(deltaY) < mItemHeight / EVEN_VALUE) {
                        smoothScrollBy(getDistance(deltaY), 50);
                    } else {
                        smoothScrollBy(getDistance(mItemHeight + deltaY), 50);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                refreshItems();
            }
        });
    }

    private int getDistance(float scrollDistance) {
        if (Math.abs(scrollDistance) <= 2) {
            return (int) scrollDistance;
        } else if (Math.abs(scrollDistance) < 12) {
            return scrollDistance > 0 ? 2 : -2;
        } else {
            return (int) (scrollDistance / 6);
        }
    }

    private void refreshItems() {
        int offset = getSelectedSolidPosition();
        int firstPosition = getFirstVisiblePosition();
        if (getChildAt(0) == null) {
            return;
        }
        int position;
        if (Math.abs(getChildAt(0).getY()) <= mItemHeight / EVEN_VALUE) {
            position = firstPosition + offset;
        } else {
            position = firstPosition + offset + 1;
        }
        if (position == mCurrentPosition) {
            return;
        }
        mCurrentPosition = position;
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onItemSelected(getSelection());
        }
        resetItems(firstPosition, position, offset);
    }

    private void resetItems(int firstPosition, int position, int offset) {
        for (int i = position - offset - 1; i < position + offset + 1; i++) {
            View itemView = getChildAt(i - firstPosition);
            if (itemView == null) {
                continue;
            }
            TextView labelTv = itemView.findViewById(mItemLabelTvId);
            if (position == i) {
                labelTv.setTextColor(mLabelSelectColor);
                itemView.setAlpha(1f);
            } else {
                labelTv.setTextColor(mLabelColor);
                int delta = Math.abs(i - position);
                double alpha = Math.pow(mAlphaGradual, delta);
                itemView.setAlpha((float) alpha);
            }
        }
    }

    /**
     * 设置滚轮的刻度列表
     *
     * @param labels
     */
    public void setLabels(List<Integer> labels) {
        mLabels = labels;
        mAdapter.setData(mLabels);
        mAdapter.notifyDataSetChanged();
        initView();
    }

    /**
     * 设置滚轮滚动监听
     *
     * @param mItemSelectedListener
     */
    public void setOnWheelItemSelectedListener(WheelItemSelectedListener mItemSelectedListener) {
        this.mItemSelectedListener = mItemSelectedListener;
    }

    /**
     * 获取滚轮的刻度列表
     *
     * @return
     */
    public List<Integer> getLabels() {
        return mLabels;
    }

    /**
     * 滚动到指定位置
     */
    @Override
    public void setSelection(final int position) {
        mHandler.post(() ->
                NumberPicker.super.setSelection(getPosition(position))
        );
    }

    private int getPosition(int position) {
        if (mLabels == null || mLabels.size() == 0) {
            return 0;
        }
        return position;
    }

    /**
     * 获取当前滚轮位置
     *
     * @return
     */
    public int getSelection() {
        int selectSolidPosition = getSelectedSolidPosition();
        if (mCurrentPosition == 0) {
            mCurrentPosition = selectSolidPosition;
        }
        int selection = (mCurrentPosition - selectSolidPosition) % mLabels.size();
        return selection < 0 ? 0 : selection;
    }

    /**
     * 获取当前滚轮位置的刻度
     *
     * @return
     */
    public Integer getSelectLabel() {
        int position = getSelection();
        position = position < 0 ? 0 : position;
        try {
            return mLabels.get(position);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 如果需要自定义滚轮每个Item，调用此方法设置自定义Item布局，自定义布局中需要一个TextView来显示滚轮刻度
     *
     * @param itemResId 布局文件Id
     * @param labelTvId 刻度TextView的资源Id
     */
    public void setWheelItemLayout(int itemResId, int labelTvId) {
        mItemLayoutId = itemResId;
        mItemLabelTvId = labelTvId;
        mAdapter = new CycleWheelViewAdapter();
        mAdapter.setData(mLabels);
        setAdapter(mAdapter);
        initView();
    }

    /**
     * 设置未选中刻度文字颜色
     *
     * @param labelColor
     */
    public void setLabelColor(int labelColor) {
        this.mLabelColor = labelColor;
        resetItems(getFirstVisiblePosition(), mCurrentPosition, mWheelSize / 2);
    }

    /**
     * 设置选中刻度文字颜色
     *
     * @param labelSelectColor
     */
    public void setLabelSelectColor(int labelSelectColor) {
        this.mLabelSelectColor = labelSelectColor;
        resetItems(getFirstVisiblePosition(), mCurrentPosition, mWheelSize / 2);
    }

    /**
     * 设置滚轮刻度透明渐变值
     *
     * @param alphaGradual
     */
    public void setAlphaGradual(float alphaGradual) {
        this.mAlphaGradual = alphaGradual;
        resetItems(getFirstVisiblePosition(), mCurrentPosition, mWheelSize / 2);
    }

    /**
     * 设置滚轮可显示的刻度数量
     *
     * @param wheelSize
     */
    public void setWheelSize(int wheelSize) {
        this.mWheelSize = wheelSize;
        initView();
    }

    /**
     * 设置块的颜色
     *
     * @param unselectedSolidColor 未选中的块的颜色
     * @param selectedSolidColor   选中的块的颜色
     */
    public void setSolid(int unselectedSolidColor, int selectedSolidColor) {
        this.solidColor = unselectedSolidColor;
        this.selectedSolidColor = selectedSolidColor;
        initView();
    }

    /**
     * 设置分割线样式
     *
     * @param dividerColor  分割线颜色
     * @param dividerHeight 分割线高度(px)
     */
    public void setDivider(int dividerColor, int dividerHeight) {
        this.dividerColor = dividerColor;
        this.dividerHeight = dividerHeight;
    }

    private void initView() {
        mItemHeight = measureHeight();
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = mItemHeight * mWheelSize;
        mAdapter.setData(mLabels);
        mAdapter.notifyDataSetChanged();
        Drawable background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                int viewWidth = getWidth();
                Paint dividerPaint = new Paint();
                dividerPaint.setColor(dividerColor);
                dividerPaint.setStrokeWidth(dividerHeight);
                Paint selectedSolidPaint = new Paint();
                selectedSolidPaint.setColor(selectedSolidColor);
                Paint solidPaint = new Paint();
                solidPaint.setColor(solidColor);
                int selectedSolidPosition = getSelectedSolidPosition();
                if (mWheelSize >= CRITICAL_SIZE_DEFAULT) {
                    canvas.drawRect(0, 0, viewWidth, mItemHeight * selectedSolidPosition, solidPaint);
                }
                canvas.drawRect(0, mItemHeight
                        * (selectedSolidPosition + 1), viewWidth, mItemHeight
                        * mWheelSize, solidPaint);
                canvas.drawRect(0, mItemHeight * selectedSolidPosition, viewWidth, mItemHeight
                        * (selectedSolidPosition + 1), selectedSolidPaint);
                canvas.drawLine(0, mItemHeight * selectedSolidPosition, viewWidth,
                        mItemHeight, dividerPaint);
                canvas.drawLine(0, mItemHeight
                        * (selectedSolidPosition + 1), viewWidth, mItemHeight
                        * (selectedSolidPosition + 1), dividerPaint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };
        setBackground(background);
    }

    /**
     * calc selectedSolidPosition
     */
    private int getSelectedSolidPosition() {
        int selectedSolidPosition;
        if (mWheelSize % EVEN_VALUE == 0) {
            selectedSolidPosition = mWheelSize / 2 - 1;
        } else {
            selectedSolidPosition = mWheelSize / 2;
        }
        return selectedSolidPosition;
    }

    private int measureHeight() {
        View itemView = LayoutInflater.from(getContext()).inflate(mItemLayoutId, null);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        itemView.measure(w, h);
        int height = itemView.getMeasuredHeight();
        return height;
    }

    public interface WheelItemSelectedListener {
        /**
         * 选中Item
         *
         * @param position
         */
        void onItemSelected(int position);
    }

    public class CycleWheelViewAdapter extends BaseAdapter {

        private List<Integer> mDataList = new ArrayList<>();

        public void setData(List<Integer> mWheelLabels) {
            mDataList.clear();
            mDataList.addAll(mWheelLabels);
        }

        @Override
        public int getCount() {
            return mDataList.size() + mWheelSize - 1;
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(mItemLayoutId, null);
            }
            TextView textView = convertView.findViewById(mItemLabelTvId);
            if (position < getSelectedSolidPosition()
                    || position >= mDataList.size() + getSelectedSolidPosition()) {
                textView.setText("");
                convertView.setVisibility(View.INVISIBLE);
            } else {
                int index = (position - getSelectedSolidPosition()) % mDataList.size();
                int label = mDataList.get(index);
                textView.setOnClickListener(v ->
                        setSelection(index)
                );
                textView.setText(String.valueOf(label));
                convertView.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

}
