package com.shouzhan.design.adapter.holder;

import android.view.ViewGroup;

import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.databinding.ItemUserFemaleBinding;
import com.shouzhan.design.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version UserFemaleViewHolder.java, v 0.1 2019-04-21 下午6:27 danbin
 */
public class UserFemaleViewHolder extends BaseBindingRecyclerViewHolder<ItemUserFemaleBinding, UserListResult> {

    public UserFemaleViewHolder(ViewGroup viewGroup, int layoutId) {
        super(viewGroup, layoutId);
    }

    @Override
    public void bindItem(UserListResult item, int position) {
        mBinding.titleTv.setText(item.getUsername());
    }
}
