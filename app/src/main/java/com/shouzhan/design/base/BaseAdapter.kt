package com.shouzhan.design.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseAdapter<T, B : ViewDataBinding>(var items: MutableList<T>, private var layoutRes: Int)
    : RecyclerView.Adapter<BaseAdapter<T, B>.ViewHolder>() {
    private var itemClickListener: ((item: T, binding: B) -> Unit)? = null
    private var itemLongClickListener: ((item: T, binding: B, position: Int) -> Boolean)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<B>(inflater, layoutRes, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setNewData(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: B) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            bindItem(binding, item)
            itemClickListener?.let { clickListener ->
                binding.root.setOnClickListener {
                    clickListener.invoke(item, binding)
                }
            }
            itemLongClickListener?.let { longClickListener ->
                binding.root.setOnLongClickListener {
                    longClickListener.invoke(item, binding, this.layoutPosition)
                }
            }
            binding.executePendingBindings()
            bindAfterExecute(binding, item)
        }

    }

    abstract fun bindItem(binding: B, item: T)

    open fun bindAfterExecute(binding: B, item: T) {}

    fun setItemClick(click: (item: T, binding: B) -> Unit) {
        itemClickListener = click
    }

    fun setItemLongClick(click: (item: T, binding: B, position: Int) -> Boolean) {
        itemLongClickListener = click
    }

    fun addData(data: List<T>) {
        items.addAll(data)
        notifyItemRangeInserted(items.size - data.size, data.size)
    }

}