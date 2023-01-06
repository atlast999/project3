package com.example.webtoapp.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import com.example.webtoapp.R
import com.example.webtoapp.base.bindings.ImageBindingAdapter.setImageDrawable
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.base.bindings.ViewBindingAdapter.viewVisibility
import com.example.webtoapp.base.util.UiText
import com.example.webtoapp.databinding.DialogBaseRequestBinding

class RequestDialog(
    private val request: DialogRequest,
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogBaseRequestBinding.inflate(inflater, container, false).apply {
            request.title?.let {
                title.viewVisibility(true)
                title.text = it.getBy(requireContext())
            }
            request.image?.let {
                image.viewVisibility(true)
                image.setImageDrawable(it)
            }
            message.text = request.message.getBy(requireContext())
            request.btnPositive?.let {
                btnPositive.viewVisibility(true)
                btnPositive.text = it.getBy(requireContext())
                btnPositive.onSafeClickListener {
                    dismissAllowingStateLoss()
                    request.onPositive.invoke()
                }
            }
            request.btnNegative?.let {
                btnNegative.viewVisibility(true)
                btnNegative.text = it.getBy(requireContext())
                btnNegative.onSafeClickListener {
                    dismissAllowingStateLoss()
                    request.onNegative.invoke()
                }
            }
        }.root
    }

    companion object {
        fun newInstance(request: DialogRequest) = RequestDialog(request)
    }
}


class DialogRequest(
    val title: UiText? = null,
    @DrawableRes val image: Int?,
    val message: UiText = UiText.from(""),
    val btnPositive: UiText? = UiText.from(R.string.label_confirm),
    val onPositive: () -> Unit = {},
    val btnNegative: UiText? = UiText.from(R.string.label_cancel),
    val onNegative: () -> Unit = {},
) {
    fun build() = RequestDialog.newInstance(request = this)
}