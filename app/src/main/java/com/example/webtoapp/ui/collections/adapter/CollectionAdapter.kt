package com.example.webtoapp.ui.collections.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.webtoapp.R
import com.example.webtoapp.base.adapter.BindingHolder
import com.example.webtoapp.base.adapter.BindingPagingAdapter
import com.example.webtoapp.base.util.onSafeClickListener
import com.example.webtoapp.databinding.ItemAppCollectionBinding
import com.example.webtoapp.model.AppCollection

class CollectionAdapter(
    private val onCollectionSelected: (AppCollection) -> Unit,
) : BindingPagingAdapter<AppCollection>(WebAppDiffCallback) {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingHolder<AppCollection, out ViewDataBinding> {
        return ViewHolder(parent)
    }

    private inner class ViewHolder(parent: ViewGroup) :
        BindingHolder<AppCollection, ItemAppCollectionBinding>(
            parent,
            R.layout.item_app_collection
        ) {

        override fun onCreated() {
            super.onCreated()
            binding.root.onSafeClickListener {
                mModel?.let(onCollectionSelected)
            }
        }

        override fun bind(model: AppCollection) {
            super.bind(model)
            binding.apply {
                tvName.text = model.name
                tvOwner.text = viewContext.getString(
                    R.string.format_collection_owner,
                    model.owner,
                )
            }
        }
    }

    object WebAppDiffCallback : DiffUtil.ItemCallback<AppCollection>() {
        override fun areItemsTheSame(oldItem: AppCollection, newItem: AppCollection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AppCollection, newItem: AppCollection): Boolean {
            return oldItem == newItem
        }

    }
}