package com.example.webtoapp.base.di

import android.content.Context
import com.example.webtoapp.BuildConfig
import com.example.webtoapp.base.credential.CredentialManager
import com.example.webtoapp.base.credential.ICredentialManager
import com.example.webtoapp.base.network.Network
import com.example.webtoapp.repository.CloudRepository
import com.example.webtoapp.repository.ICloudRepository
import com.example.webtoapp.repository.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCredentialManager(
        @ApplicationContext context: Context
    ): ICredentialManager = CredentialManager(context)

    @Provides
    @Singleton
    fun provideCloudRepository(
        apiService: ApiService,
        credentialManager: ICredentialManager,
    ): ICloudRepository = CloudRepository(apiService, credentialManager)

    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context,
        credentialManager: ICredentialManager,
    ): ApiService = createService(BuildConfig.PRIMARY_API_GATEWAY, credentialManager)
}

private inline fun <reified T> createService(
    endpoint: String,
    credentialManager: ICredentialManager
) = Network.createRetrofitInstance(
    endpoint = endpoint,
    credentialManager = credentialManager
).create<T>()