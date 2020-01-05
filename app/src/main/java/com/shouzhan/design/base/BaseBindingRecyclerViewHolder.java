package com.shouzhan.design.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author by 刘宇飞 on 2019/3/10.
 * 描述：
 */

public abstract class BaseBindingRecyclerViewHolder<VB extends ViewDataBinding, T> extends RecyclerView.ViewHolder {

    protected VB mBinding;

    public BaseBindingRecyclerViewHolder(ViewGroup viewGroup, int layoutId) {
        // 注意要依附 viewGroup，不然显示item不全!!
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
        // 得到这个View绑定的Binding
        mBinding = DataBindingUtil.getBinding(this.itemView);
    }

    /**
     * 绑定数据
     *
     * @param t        绑定的数据
     * @param position 数据索引
     */
    public abstract void bindItem(T t, int position);

}
