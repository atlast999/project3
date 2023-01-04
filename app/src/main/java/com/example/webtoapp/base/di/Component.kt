package com.example.webtoapp.base.di

import android.content.Context
import com.example.webtoapp.repository.CloudRepository
import com.example.webtoapp.repository.ICloudRepository
import com.example.webtoapp.repository.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCloudRepository(
        apiService: ApiService,
    ): ICloudRepository = CloudRepository(apiService)

    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context,
    ): ApiService =
}

private inline fun createRetrofitInstance() {

}