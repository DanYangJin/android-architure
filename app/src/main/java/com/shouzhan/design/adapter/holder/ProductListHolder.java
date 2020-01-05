package com.shouzhan.design.adapter.holder;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.ViewGroup;
import com.shouzhan.design.adapter.CategoryListAdapter;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.compontent.recyclerview.FsRecyclerViewAdapter;
import com.shouzhan.design.databinding.ItemProductListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danbin
 * @version ProductListHolder.java, v 0.1 2019-04-21 下午6:27 danbin
 */
public class ProductListHolder extends BaseBindingRecyclerViewHolder<ItemProductListBinding, String> {

    private Context mContext;

    public ProductListHolder(ViewGroup viewGroup, int layoutId, Context context) {
        super(viewGroup, layoutId);
        mContext = context;
    }

    @Override
    public void bindItem(String item, int position) {
        mBinding.productName.setText(item);
        mBinding.categoryRv.setLayoutManager(new GridLayoutManager(mContext, 3));
        CategoryListAdapter mAdapter = new CategoryListAdapter();
        List<String> categoryList = new ArrayList<>();
        categoryList.add("11111");
        categoryList.add("22222");
        categoryList.add("33333");
        mAdapter.setNewData(categoryList);
        FsRecyclerViewAdapter mLuRecyclerViewAdapter = new FsRecyclerViewAdapter(mAdapter);
        mBinding.categoryRv.setAdapter(mLuRecyclerViewAdapter);
        mBinding.categoryRv.setLoadMoreEnabled(false);
    }

}
