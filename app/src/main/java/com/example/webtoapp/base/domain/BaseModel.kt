package com.example.webtoapp.base.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

interface IParamRequest {
    fun buildQuery(): Map<String, String>
}

data class BaseResponse<T>(
    @SerializedName("message") @Expose val message: String,
    @SerializedName("data") @Expose val data: T,
)