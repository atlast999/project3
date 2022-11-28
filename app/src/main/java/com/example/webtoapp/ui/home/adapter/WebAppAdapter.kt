package com.example.webtoapp.ui.home.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.webtoapp.R
import com.example.webtoapp.base.adapter.BindingHolder
import com.example.webtoapp.base.adapter.BindingPagingAdapter
import com.example.webtoapp.databinding.ItemWebAppBinding
import com.example.webtoapp.model.WebAppInstance

class WebAppAdapter(
    diffCallback: DiffUtil.ItemCallback<WebAppInstance>,
    private val onOpenApp: (WebAppInstance) -> Unit,
) : BindingPagingAdapter<WebAppInstance>(diffCallback) {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<WebAppInstance, out ViewDataBinding> {
        return ViewHolder(parent)
    }

    private inner class ViewHolder(parent: ViewGroup) :
        BindingHolder<WebAppInstance, ItemWebAppBinding>(parent, R.layout.item_web_app) {

        override fun onCreated() {
            super.onCreated()
            binding.root.setOnClickListener {
                mModel?.let(onOpenApp)
            }
        }

        override fun bind(model: WebAppInstance) {
            super.bind(model)
            binding.apply {
                tvName.text = model.name
            }
        }
    }
}