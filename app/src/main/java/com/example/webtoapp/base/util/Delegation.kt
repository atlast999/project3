package com.example.webtoapp.base.util

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WeakReferenceDelegate<T>(
    private var weakRef: WeakReference<T>
): ReadWriteProperty<Any, T?> {
    constructor(init: () -> T): this(WeakReference(init.invoke()))

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return weakRef.get()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        value?.let {
            weakRef = WeakReference(it)
        } ?: weakRef.clear()
    }
}

fun <T> weakRef() = WeakReferenceDelegate<T?>(WeakReference(null))