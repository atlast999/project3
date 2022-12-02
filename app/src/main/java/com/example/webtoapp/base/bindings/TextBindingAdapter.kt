package com.example.webtoapp.base.bindings

import android.view.View
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
}