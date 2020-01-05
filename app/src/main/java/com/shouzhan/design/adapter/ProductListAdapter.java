package com.shouzhan.design.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.ProductListHolder;
import com.shouzhan.design.base.BaseBindingRecyclerViewAdapter;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;

/**
 * @author danbin
 * @version ProductListAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class ProductListAdapter extends BaseBindingRecyclerViewAdapter<String>{

    private Context mContext;

    public ProductListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public BaseBindingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListHolder(parent, R.layout.item_product_list, mContext);
    }

}
