package com.shouzhan.design.adapter;

import androidx.annotation.NonNull;
import android.view.ViewGroup;

import com.fshows.android.parker.recyclerview.adapter.BaseBindingRecyclerViewAdapter;
import com.fshows.android.parker.recyclerview.adapter.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.GridViewHolder;


/**
 * @author danbin
 * @version UserListAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class GridAdapter extends BaseBindingRecyclerViewAdapter<String> {

    @NonNull
    @Override
    public BaseBindingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GridViewHolder(parent, R.layout.item_grid);
    }

}
