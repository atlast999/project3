package com.example.webtoapp.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BindingPagingAdapter<Model : Any>(
    diffCallback: DiffUtil.ItemCallback<Model>,
) : BasePagingAdapter<Model, BindingHolder<Model, out ViewDataBinding>>(diffCallback) {


}

abstract class BasePagingAdapter<Model : Any, HOLDER : BindingHolder<Model, out ViewDataBinding>>(
    diffCallback: DiffUtil.ItemCallback<Model>,
) : PagingDataAdapter<Model, HOLDER>(diffCallback) {
    override fun onBindViewHolder(holder: HOLDER, position: Int) {
        getItem(position)?.let { model ->
            holder.bind(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HOLDER {
        return onCreateHolder(parent, viewType).also {
            it.onCreated()
        }
    }

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): HOLDER
}

abstract class BindingHolder<Model : Any, ViewBinding : ViewDataBinding>(
    parent: ViewGroup,
    @LayoutRes itemLayout: Int
) : RecyclerView.ViewHolder(
    DataBindingUtil.inflate<ViewBinding>(
        LayoutInflater.from(parent.context),
        itemLayout,
        parent,
        false,
    ).root
) {

    protected var mModel: Model? = null

    protected var binding: ViewBinding
        private set

    open fun onCreated() {}

    @CallSuper
    open fun bind(model: Model) {
        mModel = model
    }

    init {
        binding = DataBindingUtil.bind(itemView)!!
    }
}
