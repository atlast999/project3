package com.example.webtoapp.base.util

import android.os.SystemClock
import android.view.View

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

fun View.onSafeClickListener(interval: Int = 1000, callback: (View) -> Unit) {
    setOnClickListener(OnSafeClickListener(interval, callback))
}