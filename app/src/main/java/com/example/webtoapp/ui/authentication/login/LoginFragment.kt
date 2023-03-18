package com.example.webtoapp.ui.authentication.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.webtoapp.BR
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.base.fragment.BaseFragment
import com.example.webtoapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    override val viewModel by viewModels<LoginViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false).apply {
            btnLogin.onSafeClickListener {
                viewModel.onLogin()
            }
            tvSignup.onSafeClickListener {
                navigate(LoginFragmentDirections.toSignup())
            }
        }
}