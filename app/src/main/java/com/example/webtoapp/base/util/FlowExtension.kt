package com.example.webtoapp.base.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnLifeCycle(
    owner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) {
    owner.run {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state) {
                this@collectOnLifeCycle.collect {
                    collector.invoke(it)
                }
            }
        }
    }
}

fun <T> Flow<T>.collectLatestOnLifeCycle(
    owner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) {
    owner.run {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state) {
                this@collectLatestOnLifeCycle.collectLatest {
                    collector.invoke(it)
                }
            }
        }
    }
}