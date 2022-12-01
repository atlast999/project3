package com.example.webtoapp.base.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.webtoapp.base.util.Direction
import com.example.webtoapp.base.util.UiText
import com.example.webtoapp.base.util.collectOnLifeCycle
import com.example.webtoapp.base.viewmodel.BaseViewModel

abstract class BaseDialog : DialogFragment() {

    protected abstract val viewModel: BaseViewModel
    open var binding: ViewDataBinding? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(arguments)
            directionFlow.collectOnLifeCycle(
                owner = this@BaseDialog,
                state = Lifecycle.State.CREATED,
            ) {
                handleDirection(it)
            }
        }
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding?.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            lifecycleOwner = this@BaseDialog
            setVariable(
                getViewModelVariable(), viewModel
            )
            executePendingBindings()
        }
        viewModel.onReady()
        this.onReady()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    abstract fun getViewModelVariable(): Int

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ViewDataBinding

    open fun onReady() {}

    @CallSuper
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected fun toast(message: UiText) {
        Toast.makeText(requireContext(), message.getBy(requireContext()), Toast.LENGTH_SHORT).show()
    }

    private fun handleDirection(direction: Direction) {
        val navController by lazy { findNavController() }
        when (direction) {
            is Direction.BackWard -> navController.navigateUp()
            is Direction.NavDirection -> navController.navigate(direction.direction)
        }
    }
}