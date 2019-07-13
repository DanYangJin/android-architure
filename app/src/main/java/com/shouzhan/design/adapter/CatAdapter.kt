package com.shouzhan.design.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shouzhan.design.R
import com.shouzhan.design.adapter.holder.CatViewHolder

internal class CatAdapter : RecyclerView.Adapter<CatViewHolder>() {

    companion object {
        private val CAT_IMAGE_IDS = intArrayOf(
            R.mipmap.cat_1,
            R.mipmap.cat_2,
            R.mipmap.cat_3,
            R.mipmap.cat_4,
            R.mipmap.cat_5,
            R.mipmap.cat_6,
            R.mipmap.cat_7,
            R.mipmap.cat_8,
            R.mipmap.cat_9,
            R.mipmap.cat_10,
            R.mipmap.cat_11,
            R.mipmap.cat_12,
            R.mipmap.cat_13,
            R.mipmap.cat_14,
            R.mipmap.cat_15,
            R.mipmap.cat_16,
            R.mipmap.cat_17,
            R.mipmap.cat_18,
            R.mipmap.cat_19
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val pos = position % CAT_IMAGE_IDS.size
        holder.bindTo(CAT_IMAGE_IDS[pos])
    }

    override fun getItemCount() = CAT_IMAGE_IDS.size * 4
}