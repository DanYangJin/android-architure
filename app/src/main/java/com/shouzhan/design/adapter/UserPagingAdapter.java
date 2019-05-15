package com.shouzhan.design.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.UserPagingViewHolder;
import com.shouzhan.design.ui.user.model.javabean.DataInfo;

/**
 * @author danbin
 * @version UserPagingAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class UserPagingAdapter extends PagedListAdapter<DataInfo, UserPagingViewHolder> {

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
        DataInfo dataBean = getItem(position);
        if (dataBean == null) {
            return;
        }
        userMaleViewHolder.bindItem(dataBean, position);
    }

    private static DiffUtil.ItemCallback<DataInfo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DataInfo>() {
                @Override
                public boolean areItemsTheSame(DataInfo oldItem, DataInfo newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(DataInfo oldItem, @NonNull DataInfo newItem) {
                    return oldItem.equals(newItem);
                }
            };

}
