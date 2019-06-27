package com.shouzhan.design.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.CategoryListHolder;
import com.shouzhan.design.base.BaseBindingRecyclerViewAdapter;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;

/**
 * @author danbin
 * @version CategoryListAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class CategoryListAdapter extends BaseBindingRecyclerViewAdapter<String>{

    @NonNull
    @Override
    public BaseBindingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListHolder(parent, R.layout.item_category_list);
    }

}
