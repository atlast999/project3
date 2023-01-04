package com.example.webtoapp.base.bindings

import android.text.InputType
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.example.webtoapp.base.util.onSafeClickListener
import kotlinx.coroutines.flow.MutableStateFlow

object TextBindingAdapter {

    @BindingAdapter("bindClearTextListener")
    @JvmStatic
    fun View.bindOnClickClearText(source: MutableStateFlow<String>?) {
        if (source == null) {
            setOnClickListener(null)
        } else {
            onSafeClickListener {
                source.value = ""
            }
        }
    }

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