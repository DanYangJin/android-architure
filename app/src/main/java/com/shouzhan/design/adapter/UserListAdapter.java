package com.shouzhan.design.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.UserListViewHolder;
import com.shouzhan.design.base.BaseBindingRecyclerViewAdapter;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.ui.kotlin.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version UserListAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class UserListAdapter extends BaseBindingRecyclerViewAdapter<UserListResult>{

    @NonNull
    @Override
    public BaseBindingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserListViewHolder(parent, R.layout.item_user);
    }

}
