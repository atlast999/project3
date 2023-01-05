package com.example.webtoapp.repository

import com.example.webtoapp.base.credential.ICredentialManager
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.model.*
import com.example.webtoapp.repository.service.ApiService

class CloudRepository(
    private val service: ApiService,
    private val credentialManager: ICredentialManager,
) : ICloudRepository {
    override suspend fun signup(request: AuthenticationRequest): AuthenticationResponse {
        return service.signup(request).data
    }

    override suspend fun login(request: AuthenticationRequest): AuthenticationResponse {
        return service.login(request).data.also {
            credentialManager.saveToken(it.token)
        }
    }

    override suspend fun createWebApp(request: WebAppInstance) {
        service.createWebApp(request)
    }

    override suspend fun getMyList(request: PagingRequest): PagingModel<WebAppInstance> {
        return service.getMyList(request.buildQuery()).data
    }

    override suspend fun shareMyList(request: ShareMyWebAppListRequest) {
        service.shareMyList(request)
    }

    override suspend fun getCollectionList(request: PagingRequest): PagingModel<AppCollection> {
        return service.getCollectionList(request.buildQuery()).data
    }

    override suspend fun getCollectionDetail(
        collectionId: String,
        request: PagingRequest
    ): PagingModel<WebAppInstance> {
        return service.getCollectionDetail(collectionId, request.buildQuery()).data
    }

    override suspend fun takeCollection(collectionId: String) {
        service.takeCollection(collectionId)
    }


}