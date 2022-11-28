package com.example.webtoapp.base.bindings

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

object TextBindingAdapter {

    @BindingAdapter("bindClearTextListener")
    @JvmStatic
    fun View.bindOnClickClearText(source: MutableLiveData<String>?) {
        if (source == null) {
            setOnClickListener(null)
        } else {
            setOnClickListener {
                source.value = ""
            }
        }
    }
}