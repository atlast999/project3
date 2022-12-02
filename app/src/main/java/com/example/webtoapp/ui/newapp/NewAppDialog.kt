package com.example.webtoapp.ui.newapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.webtoapp.BR
import com.example.webtoapp.base.dialog.BaseDialog
import com.example.webtoapp.base.util.onSafeClickListener
import com.example.webtoapp.databinding.DialogNewAppBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewAppDialog : BaseDialog() {

    override val viewModel by viewModels<NewAppViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        DialogNewAppBinding.inflate(inflater, container, false).apply {
            btnNegative.onSafeClickListener {
                findNavController().navigateUp()
            }
            btnPositive.onSafeClickListener {
                Timber.d("click")
            }
        }
}