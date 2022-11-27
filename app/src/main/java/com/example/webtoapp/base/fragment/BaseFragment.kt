package com.example.webtoapp.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.webtoapp.base.util.Direction
import com.example.webtoapp.base.util.collectOnLifeCycle
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.ui.splash.SplashFragmentDirections

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel
    protected var binding: ViewDataBinding? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(arguments)
            directionFlow.collectOnLifeCycle(
                owner = this@BaseFragment,
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

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ViewDataBinding

    abstract fun getViewModelVariable(): Int



    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {
            setVariable(
                getViewModelVariable(), viewModel
            )
            executePendingBindings()
        }
    }

    private fun handleDirection(direction: Direction) {
        val navController by lazy { findNavController() }
        when (direction) {
            is Direction.BackWard -> navController.navigateUp()
            is Direction.NavDirection -> navController.navigate(direction.direction)
        }
    }
}