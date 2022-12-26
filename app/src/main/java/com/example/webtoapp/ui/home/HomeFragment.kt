package com.example.webtoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.webtoapp.BR
import com.example.webtoapp.R
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.base.fragment.waitForBackStackEntryData
import com.example.webtoapp.base.util.collectLatestOnLifeCycle
import com.example.webtoapp.base.util.collectOnLifeCycle
import com.example.webtoapp.databinding.FragmentHomeBinding
import com.example.webtoapp.model.WebAppInstance
import com.example.webtoapp.ui.home.adapter.WebAppAdapter
import dagger.hilt.android.AndroidEntryPoint

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
            webAppAdapter.loadStateFlow.collectOnLifeCycle(viewLifecycleOwner) {
                prependProgress.isVisible =
                    it.source.prepend is LoadState.Loading || it.source.refresh is LoadState.Loading
                appendProgress.isVisible = it.source.append is LoadState.Loading

            }
            viewModel.webAppDataFlow.collectLatestOnLifeCycle(viewLifecycleOwner) {
                webAppAdapter.submitData(it)
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
                when (menuItem.itemId) {
                    R.id.item_new_app -> {
                        waitForBackStackEntryData<WebAppInstance>(key = "NEW_APP") {
                            viewModel.onNewAppAdded(it)
                        }
                        findNavController().navigate(
                            HomeFragmentDirections.toNewApp()
                        )
                        return@createOptionMenu true
                    }
                    R.id.item_share_collection -> {
                        findNavController().navigate(
                            HomeFragmentDirections.toShare()
                        )
                        return@createOptionMenu true
                    }
                    R.id.item_get_collection -> {
                        findNavController().navigate(
                            HomeFragmentDirections.toFindCollection()
                        )
                    }
                }
                return@createOptionMenu false
            }
        )
    }

    override fun getViewModelVariable(): Int = BR.vm
}