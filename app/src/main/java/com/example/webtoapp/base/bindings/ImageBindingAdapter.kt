package com.example.webtoapp.base.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.webtoapp.R

object ImageBindingAdapter {

    @BindingAdapter("imgSrc")
    @JvmStatic
    fun ImageView.setImage(path: String?) {
        path ?: return
        Glide.with(context)
            .load(path)
            .placeholder(R.drawable.ic_placeholder)
            .into(this)
    }
}