package com.example.webtoapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationRequest(
    @SerializedName("username") @Expose val username: String,
    @SerializedName("password") @Expose val password: String,
)

data class UserDto(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("username") @Expose val username: String,
)

data class AuthenticationResponse(
    @SerializedName("user") @Expose val user: UserDto,
    @SerializedName("token") @Expose val token: String,
)
