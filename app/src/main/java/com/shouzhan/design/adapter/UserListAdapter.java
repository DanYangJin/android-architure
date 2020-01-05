package com.shouzhan.design.adapter;

import androidx.annotation.NonNull;
import android.view.ViewGroup;

import com.shouzhan.design.R;
import com.shouzhan.design.adapter.holder.UserFemaleViewHolder;
import com.shouzhan.design.adapter.holder.UserMaleViewHolder;
import com.shouzhan.design.model.annotation.UserGenderType;
import com.shouzhan.design.base.BaseBindingRecyclerViewAdapter;
import com.shouzhan.design.base.BaseBindingRecyclerViewHolder;
import com.shouzhan.design.model.remote.result.UserListResult;

/**
 * @author danbin
 * @version UserListAdapter.java, v 0.1 2019-04-21 下午6:34 danbin
 */
public class UserListAdapter extends BaseBindingRecyclerViewAdapter<UserListResult>{

    @NonNull
    @Override
    public BaseBindingRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == UserGenderType.FEMALE) {
            return new UserFemaleViewHolder(parent, R.layout.item_user_female);
        } else  {
            return new UserMaleViewHolder(parent, R.layout.item_user_male);
        }
    }

}
