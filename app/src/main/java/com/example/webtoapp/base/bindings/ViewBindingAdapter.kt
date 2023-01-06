package com.example.webtoapp.base.bindings

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.flow.MutableStateFlow

object ViewBindingAdapter {

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

    class OnSafeClickListener(
        private val interval: Int,
        private val callback: (View) -> Unit,
    ) : View.OnClickListener {

        private val mLock = Any()
        private var lastClickedTime = 0L

        override fun onClick(view: View?) {
            if (view == null) return
            synchronized(mLock) {
                SystemClock.elapsedRealtime().let { current ->
                    if (current - lastClickedTime > interval) {
                        lastClickedTime = current
                        callback(view)
                    }
                }
            }
        }

    }

    //    @BindingAdapter("onSafeClick")
//    @JvmStatic
    fun View.onSafeClickListener(interval: Int = 1000, callback: (View) -> Unit) {
        setOnClickListener(OnSafeClickListener(interval, callback))
    }

    @BindingAdapter("viewVisibility")
    @JvmStatic
    fun View.viewVisibility(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }
}