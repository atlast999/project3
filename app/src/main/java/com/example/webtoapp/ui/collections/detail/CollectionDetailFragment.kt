package com.example.webtoapp.ui.collections.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webtoapp.BR
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.base.util.collectLatestOnLifeCycle
import com.example.webtoapp.databinding.FragmentCollectionDetailBinding
import com.example.webtoapp.ui.collections.detail.adapter.CollectionDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionDetailFragment : BaseFragment() {

    override val viewModel by viewModels<CollectionDetailViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    private lateinit var collectionDetailAdapter: CollectionDetailAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCollectionDetailBinding.inflate(inflater, container, false).apply {
            recApp.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CollectionDetailAdapter().also {
                    collectionDetailAdapter = it
                }
            }
            viewModel.webAppDataFlow.collectLatestOnLifeCycle(viewLifecycleOwner) {
                collectionDetailAdapter.submitData(it)
            }
            btnTake.onSafeClickListener {
                viewModel.takeCollection()
            }
        }
}