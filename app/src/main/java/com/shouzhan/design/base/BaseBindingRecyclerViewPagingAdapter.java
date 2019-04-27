package com.shouzhan.design.base;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.shouzhan.design.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by 刘宇飞 on 2019/3/10.
 * 描述：
 */

public abstract class BaseBindingRecyclerViewPagingAdapter<T> extends PagedListAdapter<T, BaseBindingRecyclerViewHolder> {

    private static final String TAG = BaseBindingRecyclerViewPagingAdapter.class.getSimpleName();

    protected List<T> items = new ArrayList<>();

    protected BaseBindingRecyclerViewPagingAdapter() {
        this(new DiffUtil.ItemCallback<T>() {
            @Override
            public boolean areItemsTheSame(@NonNull T t, @NonNull T t1) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull T t, @NonNull T t1) {
                return false;
            }
        });
    }

    protected BaseBindingRecyclerViewPagingAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseBindingRecyclerViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: ####");
        holder.bindItem(getItem(position), position);
        holder.mBinding.setVariable(BR.item, getItem(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e(TAG, "getItemViewType: ####");
        if (getItem(position) instanceof BaseRecyclerViewType) {
            return ((BaseRecyclerViewType) getItem(position)).getViewType();
        }
        return super.getItemViewType(position);
    }

    /**
     * 获取每个Item的数据源
     *
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
