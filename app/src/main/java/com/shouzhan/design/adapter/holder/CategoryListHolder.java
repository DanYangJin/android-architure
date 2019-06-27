package com.shouzhan.design.adapter.holder;

import android.view.ViewGroup;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.databinding.ItemCategoryListBinding;

/**
 * @author danbin
 * @version CategoryListHolder.java, v 0.1 2019-04-21 下午6:27 danbin
 */
public class CategoryListHolder extends BaseBindingRecyclerViewHolder<ItemCategoryListBinding, String> {

    public CategoryListHolder(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void bindItem(String item, int position) {
        mBinding.categoryName.setText(item);
    }

}
