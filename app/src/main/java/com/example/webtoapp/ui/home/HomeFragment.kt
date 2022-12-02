package com.example.webtoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.webtoapp.BR
import com.example.webtoapp.R
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.base.util.UiText
import com.example.webtoapp.base.util.onSafeClickListener
import com.example.webtoapp.databinding.FragmentHomeBinding
import com.example.webtoapp.ui.home.adapter.WebAppAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    override val viewModel by viewModels<HomeViewModel>()
    private lateinit var webAppAdapter: WebAppAdapter
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false).apply {
            recApp.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = WebAppAdapter {
                    findNavController().navigate(
                        HomeFragmentDirections.toBrowser(it)
                    )
                }.also {
                    webAppAdapter = it
                }
            }
            btn.onSafeClickListener {
                toast(UiText.from(viewModel.searchQuery.value))
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    webAppAdapter.loadStateFlow.collect {
                        prependProgress.isVisible =
                            it.source.prepend is LoadState.Loading || it.source.refresh is LoadState.Loading
                        appendProgress.isVisible = it.source.append is LoadState.Loading

                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.webAppDataFlow.collectLatest {
                        webAppAdapter.submitData(it)
                    }
                }
            }
        }

    override fun onReady() {
        super.onReady()
        createOptionMenu(
            onCreate = { menu, menuInflater ->
                menu.clear()
                menuInflater.inflate(R.menu.home_menu, menu)
            },
            onItemSelected = { menuItem ->
                if (menuItem.itemId == R.id.item_new_app) {
                    findNavController().navigate(
                        HomeFragmentDirections.toNewApp()
                    )
                    return@createOptionMenu true
                }
                return@createOptionMenu false
            }
        )
    }

    override fun getViewModelVariable(): Int = BR.vm
}