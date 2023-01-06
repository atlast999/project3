package com.example.webtoapp.base.bindings

import android.text.InputType
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

object TextBindingAdapter {

    @BindingAdapter("bindInputType")
    @JvmStatic
    fun AppCompatEditText.bindInputType(type: Int?) {
        if (type == InputType.TYPE_NULL) {
            clearFocus()
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = false
        } else {
            isFocusable = true
            isFocusableInTouchMode = true
            isClickable = true
        }
        inputType = type ?: InputType.TYPE_CLASS_TEXT
    }
}