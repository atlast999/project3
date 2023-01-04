package com.example.webtoapp.base.credential

interface ICredentialManager {
    fun getUserId(): String?
    fun saveUserId(userId: String)
    fun getToken(): String?
    fun saveToken(token: String)
}