package com.example.webtoapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebAppInstance(
    @SerializedName("id") @Expose val id: String = "",
    @SerializedName("name") @Expose val name: String,
    @SerializedName("url") @Expose val url: String,
    @SerializedName("image") @Expose val image: String,
) : Parcelable

data class ShareMyWebAppListRequest(
    @SerializedName("collection_name") @Expose val collectionName: String,
)

data class AppCollection(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("owner_id") @Expose val owner: String,
)