package com.example.webtoapp.base.credential

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("credential")

class CredentialManager(private val context: Context) : ICredentialManager {
    private val userIdKey = stringPreferencesKey("USER_ID")
    private val tokenKey = stringPreferencesKey("TOKEN")

    override fun getUserId(): String? {
        return runBlocking {
            context.dataStore.data.firstOrNull()?.get(userIdKey)
        }
    }

    override fun saveUserId(userId: String) {
        runBlocking {
            context.dataStore.edit {
                it[userIdKey] = userId
            }
        }
    }

    override fun getToken(): String? {
        return runBlocking {
            context.dataStore.data.firstOrNull()?.get(tokenKey)
        }
    }

    override fun saveToken(token: String) {
        runBlocking {
            context.dataStore.edit {
                it[tokenKey] = token
            }
        }
    }
}