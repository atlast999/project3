package com.example.webtoapp.ui.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webtoapp.BR
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.base.util.collectLatestOnLifeCycle
import com.example.webtoapp.databinding.FragmentFindCollectionBinding
import com.example.webtoapp.ui.collections.adapter.CollectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindCollectionFragment : BaseFragment() {

    override val viewModel by viewModels<FindCollectionViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    private lateinit var appCollectionAdapter: CollectionAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFindCollectionBinding.inflate(inflater, container, false).apply {
            recCollection.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CollectionAdapter {
                    navigate(
                        FindCollectionFragmentDirections.toDetail(it.id)
                    )
                }.also {
                    appCollectionAdapter = it
                }
            }
            viewModel.webAppDataFlow.collectLatestOnLifeCycle(viewLifecycleOwner) {
                appCollectionAdapter.submitData(it)
            }
        }
}