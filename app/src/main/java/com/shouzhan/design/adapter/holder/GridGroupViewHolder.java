package com.shouzhan.design.adapter.holder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.fshows.android.parker.recyclerview.adapter.BaseBindingRecyclerViewHolder;
import com.google.common.collect.Lists;
import com.shouzhan.design.R;
import com.shouzhan.design.adapter.GridAdapter;
import com.shouzhan.design.databinding.ItemGridBinding;
import com.shouzhan.design.databinding.ItemGridGroupBinding;

import java.util.List;


/**
 * @author danbin
 * @version UserListViewHolder.java, v 0.1 2019-04-21 下午6:27 danbin
 */
public class GridGroupViewHolder extends BaseBindingRecyclerViewHolder<ItemGridGroupBinding, String> {

    private static final int MAX = 30;

    public GridGroupViewHolder(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(String s, int j) {
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mBinding.getRoot().getContext(), 4));
        GridAdapter mGridAdapter = new GridAdapter();
        mBinding.recyclerView.setAdapter(mGridAdapter);
//        mBinding.recyclerView.setPullRefreshEnabled(false);
        List<String> dataList = Lists.newArrayList();
        for (int i = 0; i < MAX; i++) {
            dataList.add(String.valueOf(i));
        }
        mGridAdapter.addAll(dataList);
    }

}
