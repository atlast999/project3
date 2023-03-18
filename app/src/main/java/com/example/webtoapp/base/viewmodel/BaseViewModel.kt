package com.example.webtoapp.base.viewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.NavDirections
import com.example.webtoapp.R
import com.example.webtoapp.base.dialog.DialogRequest
import com.example.webtoapp.base.util.Direction
import com.example.webtoapp.base.util.UiText
import com.example.webtoapp.base.util.weakRef
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    var argsRef by weakRef<Bundle>()
    private val mDirectionChanel = Channel<Direction>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val directionFlow = mDirectionChanel.receiveAsFlow()
    val dialogRequestFlow = MutableSharedFlow<DialogRequest>()

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

    protected fun navigateUp() {
        viewModelScope.launch {
            mDirectionChanel.send(Direction.BackWard)
        }
    }

    protected fun showError(message: UiText) {
        viewModelScope.launch {
            dialogRequestFlow.emit(
                DialogRequest(
                    image = R.drawable.ic_error,
                    message = message,
                    btnPositive = null,
                )
            )
        }
    }

    protected val defaultExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            showError(
                throwable.message?.let { UiText.from(it) }
                    ?: UiText.from(R.string.msg_error_default)
            )
        }
    }

    protected fun runCoroutineTask(
        coroutineContext: CoroutineContext = defaultExceptionHandler,
        block: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launch(coroutineContext) {
        block.invoke(this)
    }


}

@MainThread
inline fun <reified Args : NavArgs> BaseViewModel.navArgs(): NavArgsLazy<Args> =
    NavArgsLazy(Args::class) {
        argsRef ?: throw IllegalStateException("ViewModel $this has null arguments")
    }
