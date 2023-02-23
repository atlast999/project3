package com.example.webtoapp.repository

import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.model.*

interface ICloudRepository {

    suspend fun signup(request: AuthenticationRequest): AuthenticationResponse

    /**
     * An abstract function which perform login task at repository level
     * @see CloudRepository.login for implementation
     */
    suspend fun login(request: AuthenticationRequest): AuthenticationResponse

    suspend fun createWebApp(request: WebAppInstance)

    suspend fun getMyList(request: PagingRequest): PagingModel<WebAppInstance>

    suspend fun shareMyList(request: ShareMyWebAppListRequest)

    suspend fun getCollectionList(request: PagingRequest): PagingModel<AppCollection>

    suspend fun getCollectionDetail(
        collectionId: String,
        request: PagingRequest
    ): PagingModel<WebAppInstance>

    suspend fun takeCollection(collectionId: String)
}