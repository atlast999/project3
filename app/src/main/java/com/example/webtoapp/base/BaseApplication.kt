package com.example.webtoapp.base

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.annotation.CallSuper

abstract class BaseApplication : Application() {

    companion object {
        private lateinit var instance: BaseApplication
        fun getInstance() = instance
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isDebuggableMode() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
}