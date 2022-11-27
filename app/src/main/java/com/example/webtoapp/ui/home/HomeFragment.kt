package com.example.webtoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.webtoapp.BR
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    override val viewModel: BaseViewModel by viewModels<HomeViewModel>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false).apply {

        }

    override fun getViewModelVariable(): Int = BR.vm
}