package com.shouzhan.design.compontent.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * @author danbin
 * @version CustomLinearLayout.java, v 0.1 2020-03-10 10:20 AM danbin
 */
class CustomRelativeLayout(private val mContext: Context, attrs: AttributeSet?) : RelativeLayout(mContext, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("xss", "viewGroup dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("xss", "viewGroup onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("xss", "viewGroup onTouchEvent")
        return super.onTouchEvent(event)
    }

}