package com.shouzhan.design.compontent.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

/**
 * @author danbin
 * @version CustomButton.java, v 0.1 2020-03-10 10:20 AM danbin
 */
class CustomButton(private val mContext: Context, attrs: AttributeSet?) : AppCompatButton(mContext, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("xss", "view dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("xss", "view onTouchEvent: " + super.onTouchEvent(event))
        return super.onTouchEvent(event)
    }

}