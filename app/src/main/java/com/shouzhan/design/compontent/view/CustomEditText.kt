package com.shouzhan.design.compontent.view;

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.shouzhan.design.R
import kotlinx.android.synthetic.main.layout_custom_et.view.*

/**
 * @author danbin
 * @version CustomEditText.java, v 0.1 2019-12-16 15:53 danbin
 */
class CustomEditText(private val mContext: Context, attrs: AttributeSet?) : RelativeLayout(mContext, attrs) {

    init {
        LayoutInflater.from(mContext).inflate(R.layout.layout_custom_et, this, true)
        if (attrs != null) {
            val a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
            input_et.setText(a.getString(R.styleable.CustomEditText_input_text))
            a.recycle()
        }
    }

}
