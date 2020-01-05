package com.shouzhan.design.adapter.holder

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.google.android.flexbox.FlexboxLayoutManager
import com.shouzhan.design.R

internal class CatViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView = itemView.findViewById(R.id.cat_iv)

    internal fun bindTo(@DrawableRes drawableRes: Int) {
        imageView.setImageResource(drawableRes)
        val lp = imageView.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.flexGrow = 1f
        }
    }
}