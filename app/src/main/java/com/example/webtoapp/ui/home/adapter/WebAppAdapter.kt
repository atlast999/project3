package com.example.webtoapp.ui.home.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.webtoapp.R
import com.example.webtoapp.base.adapter.BindingHolder
import com.example.webtoapp.base.adapter.BindingPagingAdapter
import com.example.webtoapp.base.bindings.ImageBindingAdapter.setImage
import com.example.webtoapp.base.bindings.ViewBindingAdapter.onSafeClickListener
import com.example.webtoapp.databinding.ItemWebAppBinding
import com.example.webtoapp.model.WebAppInstance

class WebAppAdapter(
    private val onOpenApp: (WebAppInstance) -> Unit,
) : BindingPagingAdapter<WebAppInstance>(WebAppDiffCallback) {

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
            binding.root.onSafeClickListener {
                mModel?.let(onOpenApp)
            }
        }

        override fun bind(model: WebAppInstance) {
            super.bind(model)
            binding.apply {
                tvName.text = model.name
                appImage.setImage(model.image)
            }
        }
    }

    object WebAppDiffCallback : DiffUtil.ItemCallback<WebAppInstance>() {
        override fun areItemsTheSame(oldItem: WebAppInstance, newItem: WebAppInstance): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WebAppInstance, newItem: WebAppInstance): Boolean {
            return oldItem == newItem
        }

    }
}