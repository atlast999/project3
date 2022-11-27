package com.example.webtoapp.base.bindings

import android.view.View
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.flow.MutableStateFlow

object TextBindingAdapter {

    @BindingAdapter("bindClearTextListener")
    @JvmStatic
    fun View.bindOnClickClearText(source: MutableStateFlow<String>?) {
        if (source == null) {
            setOnClickListener(null)
        } else {
            setOnClickListener {
                source.value = ""
            }
        }
    }
}