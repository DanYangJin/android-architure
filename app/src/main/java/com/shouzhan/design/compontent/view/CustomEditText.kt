package com.shouzhan.design.compontent.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
            input_et.setText(a.getString(R.styleable.CustomEditText_inputText))
            a.recycle()
        }
    }

    fun setEtText(inputTxt: String) {
        input_et.setText(inputTxt)
    }

    fun getEtText(): String {
        return input_et.text.toString()
    }

    fun getEt(): EditText {
        return input_et
    }

    companion object {

        @BindingAdapter(value = ["app:inputText"], requireAll = false)
        @JvmStatic
        fun setInputText(view: CustomEditText, value: String?) {
            value?.let {
                view.setEtText(it)
            }
        }

        @InverseBindingAdapter(attribute = "app:inputText", event = "app:inputTextAttrChanged")
        @JvmStatic
        fun getInputText(view: CustomEditText): String? {
            return if ("" == view.getEtText()) {
                null
            } else {
                view.getEtText()
            }
        }

        @BindingAdapter("app:inputTextAttrChanged")
        @JvmStatic
        fun setListeners(view: CustomEditText, listener: InverseBindingListener?) {
            view.getEt().addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    listener?.onChange()
                }
            })
        }

    }


}
