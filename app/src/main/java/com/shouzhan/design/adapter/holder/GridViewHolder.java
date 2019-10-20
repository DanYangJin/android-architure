package com.shouzhan.design.adapter.holder;

import android.view.ViewGroup;

import com.fshows.android.parker.recyclerview.adapter.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.databinding.ItemGridBinding;
import com.shouzhan.design.databinding.ItemGridGroupBinding;


/**
 * @author danbin
 * @version UserListViewHolder.java, v 0.1 2019-04-21 下午6:27 danbin
 */
public class GridViewHolder extends BaseBindingRecyclerViewHolder<ItemGridBinding, String> {

    public GridViewHolder(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(String s, int i) {
        mBinding.tvTitle.setText(s);
    }

}
