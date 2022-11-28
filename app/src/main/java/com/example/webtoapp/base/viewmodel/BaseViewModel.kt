package com.example.webtoapp.base.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.webtoapp.base.util.Direction
import com.example.webtoapp.base.util.weakRef
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application){

    private var argsRef by weakRef<Bundle>()
    private val mDirectionChanel = Channel<Direction>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val directionFlow = mDirectionChanel.receiveAsFlow()

    @CallSuper
    open fun onBind(argument: Bundle?) {
        argsRef = argument
    }

    open fun onReady() {

    }

    protected fun navigate(direction: NavDirections) {
        viewModelScope.launch {
            mDirectionChanel.send(Direction.NavDirection(direction))
        }
    }

}