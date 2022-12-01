package com.example.webtoapp.ui.newapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.webtoapp.BR
import com.example.webtoapp.base.dialog.BaseDialog
import com.example.webtoapp.databinding.DialogNewAppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewAppDialog : BaseDialog() {

    override val viewModel by viewModels<NewAppViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        DialogNewAppBinding.inflate(inflater, container, false).apply {

        }
}