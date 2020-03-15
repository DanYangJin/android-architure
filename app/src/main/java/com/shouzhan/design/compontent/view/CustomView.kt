package com.shouzhan.design.compontent.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * @author danbin
 * @version CustomView.java, v 0.1 2020-03-12 5:44 PM danbin
 */

class CustomView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val withMode = MeasureSpec.getMode(widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        Log.e("CustomView", "widthMeasureSpec: $withMode , $width")
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        Log.e("CustomView", "heightMeasureSpec: $heightMode , $height")
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e("CustomView", "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.e("CustomView", "onDraw")
    }

}