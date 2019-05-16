package com.shouzhan.design.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.UserPagingViewHolder;
import com.shouzhan.design.ui.user.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version UserPagingAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class UserPagingAdapter extends PagedListAdapter<UserListResult, UserPagingViewHolder> {

    public UserPagingAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public UserPagingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserPagingViewHolder(viewGroup, R.layout.item_user_paging);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPagingViewHolder userMaleViewHolder, int position) {
        UserListResult dataBean = getItem(position);
        if (dataBean == null) {
            return;
        }
        userMaleViewHolder.bindItem(dataBean, position);
    }

    private static DiffUtil.ItemCallback<UserListResult> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserListResult>() {
                @Override
                public boolean areItemsTheSame(UserListResult oldItem, UserListResult newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(UserListResult oldItem, @NonNull UserListResult newItem) {
                    return oldItem.equals(newItem);
                }
            };

}
