package com.shouzhan.design.adapter

import com.shouzhan.design.R
import com.shouzhan.design.base.BaseAdapter
import com.shouzhan.design.databinding.ItemUserBinding
import com.shouzhan.design.model.remote.result.UserListResult

class UserListAdapter(items: MutableList<UserListResult>)
	: BaseAdapter<UserListResult, ItemUserBinding>(items, R.layout.item_user) {

	override fun bindItem(binding: ItemUserBinding, item: UserListResult) {
		binding.titleTv.text = item.username
	}

}