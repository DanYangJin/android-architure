package com.shouzhan.design.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.shouzhan.design.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by 刘宇飞 on 2019/3/10.
 * 描述：
 */

public abstract class BaseBindingRecyclerViewAdapter<T>  extends RecyclerView.Adapter<BaseBindingRecyclerViewHolder>{

    private static final String TAG = BaseBindingRecyclerViewAdapter.class.getSimpleName();

    protected List<T> items = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull BaseBindingRecyclerViewHolder holder, final int position) {
        holder.bindItem(getItem(position), position);
        holder.mBinding.setVariable(BR.item, getItem(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
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
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setNewData(List<T> data) {
        this.items.clear();
        this.items.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.items.addAll(data);
        notifyItemRangeInserted(this.items.size() - data.size(), data.size());
    }

    /**
     * 判断该位置是要固定
     *
     * @param position adapter position
     * @return true or false
     */
    public boolean isPinnedPosition(int position) {
        return false;
    }

}
