package com.example.webtoapp.base.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.webtoapp.base.dialog.DialogRequest
import com.example.webtoapp.base.util.Direction
import com.example.webtoapp.base.util.UiText
import com.example.webtoapp.base.util.collectOnLifeCycle
import com.example.webtoapp.base.viewmodel.BaseViewModel

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel
    open var binding: ViewDataBinding? = null
    private val navController by lazy { findNavController() }

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
            dialogRequestFlow.collectOnLifeCycle(
                owner = this@BaseFragment,
                state = Lifecycle.State.CREATED
            ) {
                dispatchDialogRequest(it)
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
            lifecycleOwner = this@BaseFragment
            setVariable(
                getViewModelVariable(), viewModel
            )
            executePendingBindings()
        }
        viewModel.onReady()
        this.onReady()
    }

    abstract fun getViewModelVariable(): Int

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): ViewDataBinding

    open fun onReady() {}

    protected fun createOptionMenu(
        @MenuRes menuRes: Int,
        onCreate: (Menu) -> Unit = { it.clear() },
        onItemSelected: (MenuItem) -> Boolean = { false }
    ) {
        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    onCreate.invoke(menu)
                    menuInflater.inflate(menuRes, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return onItemSelected.invoke(menuItem)
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    protected fun toast(message: UiText) {
        Toast.makeText(requireContext(), message.getBy(requireContext()), Toast.LENGTH_SHORT).show()
    }

    protected fun navigate(direction: NavDirections) {
        handleDirection(Direction.NavDirection(direction))
    }

    protected fun navigateUp() {
        handleDirection(Direction.BackWard)
    }

    private fun handleDirection(direction: Direction) {
        when (direction) {
            is Direction.BackWard -> navController.navigateUp()
            is Direction.NavDirection -> navController.navigate(direction.direction)
        }
    }

    private fun dispatchDialogRequest(request: DialogRequest) {
        val dialog = request.build()
        dialog.show(requireActivity().supportFragmentManager, "RequestDialog")
    }

}

fun <T> Fragment.waitForBackStackEntryData(
    key: String,
    callback: (T) -> Unit,
) {
    val entry = findNavController().currentBackStackEntry ?: return
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            entry.savedStateHandle.getLiveData<T>(key).let { liveResult ->
                liveResult.observe(viewLifecycleOwner) { result ->
                    result ?: return@observe
                    callback.invoke(result)
                    entry.savedStateHandle.remove<T>(key)
                    liveResult.removeObservers(viewLifecycleOwner)
                }
            }
        }
    }
    entry.lifecycle.addObserver(observer)
    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            entry.lifecycle.removeObserver(observer)
        }
    })
}

fun <T> Fragment.finishWithBackStackEntryData(key: String, data: T) {
    findNavController().run {
        previousBackStackEntry?.savedStateHandle?.set(key, data)
        navigateUp()
    }
}