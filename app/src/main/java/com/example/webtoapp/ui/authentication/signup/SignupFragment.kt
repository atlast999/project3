package com.example.webtoapp.ui.authentication.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.webtoapp.BR
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment() {
    override val viewModel by viewModels<SignupViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSignupBinding.inflate(inflater, container, false).apply {
            btnLogin.onSafeClickListener {
                viewModel.onSignup()
            }
        }
}