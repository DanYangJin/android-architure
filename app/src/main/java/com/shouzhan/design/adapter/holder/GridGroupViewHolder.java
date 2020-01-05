package com.shouzhan.design.adapter.holder;

import androidx.recyclerview.widget.GridLayoutManager;
import android.view.ViewGroup;

import com.google.common.collect.Lists;
import com.shouzhan.design.adapter.GridAdapter;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;
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
    public void bindItem(String s, int position) {
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mBinding.getRoot().getContext(), 4));
        GridAdapter mGridAdapter = new GridAdapter();
        mBinding.recyclerView.setAdapter(mGridAdapter);
        List<String> dataList = Lists.newArrayList();
        for (int i = 0; i < MAX; i++) {
            dataList.add(String.valueOf(i));
        }
        mGridAdapter.addAll(dataList);
    }

//    @Override
//    public void onBindViewHolder(String s, int j) {
//        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mBinding.getRoot().getContext(), 4));
//        GridAdapter mGridAdapter = new GridAdapter();
//        mBinding.recyclerView.setAdapter(mGridAdapter);
//        List<String> dataList = Lists.newArrayList();
//        for (int i = 0; i < MAX; i++) {
//            dataList.add(String.valueOf(i));
//        }
//        mGridAdapter.addAll(dataList);
//    }

}
