package com.example.webtoapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebAppInstance(
    val id: String,
    val url: String,
    val image: String,
    val name: String,
) : Parcelable

data class AppCollection(
    val id: String,
    val name: String,
    val owner: String,
)