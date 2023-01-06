package com.example.webtoapp.ui.share

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.webtoapp.BR
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.base.dialog.BaseDialog
import com.example.webtoapp.databinding.DialogShareCollectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareCollectionDialog : BaseDialog() {
    override val viewModel by viewModels<ShareCollectionViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        DialogShareCollectionBinding.inflate(inflater, container, false).apply {

            btnNegative.onSafeClickListener {
                findNavController().navigateUp()
            }
            btnPositive.onSafeClickListener {
                viewModel.onSubmit()
            }
        }
}