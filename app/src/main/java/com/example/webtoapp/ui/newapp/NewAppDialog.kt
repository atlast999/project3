package com.example.webtoapp.ui.newapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.webtoapp.BR
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.base.dialog.BaseDialog
import com.example.webtoapp.base.fragment.finishWithBackStackEntryData
import com.example.webtoapp.databinding.DialogNewAppBinding
import com.example.webtoapp.model.WebAppInstance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewAppDialog : BaseDialog() {
    private var pickPhotoLauncher: ActivityResultLauncher<String>? = null
    override val viewModel by viewModels<NewAppViewModel>()

    override fun getViewModelVariable(): Int = BR.vm

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        DialogNewAppBinding.inflate(inflater, container, false).apply {
            btnNegative.onSafeClickListener {
                findNavController().navigateUp()
            }
            btnPositive.onSafeClickListener {
                finishWithBackStackEntryData(
                    key = "NEW_APP",
                    data = WebAppInstance(
                        url = viewModel.stateAppUrl.value,
                        image = viewModel.stateAppImage.value ?: "",
                        name = viewModel.stateAppName.value,
                    )
                )
            }
            appImage.onSafeClickListener {
                pickPhotoLauncher?.launch("Hi there!")
            }
        }

    override fun onReady() {
        super.onReady()
        pickPhotoLauncher = registerForActivityResult(RequestExternalImagePicker()) {
            it ?: return@registerForActivityResult
            viewModel.stateAppImage.value = it.toString()
        }
    }

    class RequestExternalImagePicker : ActivityResultContract<String, Uri?>() {

        override fun createIntent(context: Context, input: String): Intent =
            (Intent.ACTION_GET_CONTENT to Intent.ACTION_PICK).transform {
                Intent(it).apply {
                    type = "image/*"
                }
            }.let {
                Intent.createChooser(it.first, input).apply {
                    putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(it.second))
                }
            }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            if (resultCode != Activity.RESULT_OK) {
                return null
            }
            return intent?.data
        }

        private fun <T, R> Pair<T, T>.transform(fn: (T) -> R): Pair<R, R> = fn(first) to fn(second)

    }
}