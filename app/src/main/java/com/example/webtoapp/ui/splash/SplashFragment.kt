package com.example.webtoapp.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.webtoapp.BR
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    override val viewModel: BaseViewModel by viewModels<SplashViewModel>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSplashBinding.inflate(inflater, container, false)

    override fun getViewModelVariable(): Int = BR.vm

}