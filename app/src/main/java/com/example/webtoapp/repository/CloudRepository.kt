package com.example.webtoapp.repository

import com.example.webtoapp.base.credential.ICredentialManager
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.repository.BaseRepository
import com.example.webtoapp.base.serialize.Serializer
import com.example.webtoapp.model.*
import com.example.webtoapp.repository.service.ApiService

class CloudRepository(
    private val service: ApiService,
    private val credentialManager: ICredentialManager,
    serializer: Serializer,
) : BaseRepository(serializer), ICloudRepository {
    override suspend fun signup(request: AuthenticationRequest): AuthenticationResponse {
        return unwrap {
            service.signup(request)
        }.also {
            credentialManager.saveToken(it.token)
        }
    }

    /**
     * The function using retrofit api to make HTTP request to the back-end server
     * and then save the token into private storage for other api calls
     * @param request: contains user authentication information
     * @see AuthenticationRequest
     * @return response from server which contains access token
     * @see AuthenticationResponse
     */
    override suspend fun login(request: AuthenticationRequest): AuthenticationResponse {
        return unwrap {
            service.login(request)
        }.also {
            credentialManager.saveToken(it.token)
        }
    }

    override suspend fun createWebApp(request: WebAppInstance) {
        return unwrap {
            service.createWebApp(request)
        }
    }

    override suspend fun getMyList(request: PagingRequest): PagingModel<WebAppInstance> {
        return unwrap {
            service.getMyList(request.buildQuery())
        }
    }

    override suspend fun shareMyList(request: ShareMyWebAppListRequest) {
        return unwrap {
            service.shareMyList(request)
        }
    }

    override suspend fun getCollectionList(request: PagingRequest): PagingModel<AppCollection> {
        return unwrap {
            service.getCollectionList(request.buildQuery())
        }
    }

    override suspend fun getCollectionDetail(
        collectionId: String,
        request: PagingRequest
    ): PagingModel<WebAppInstance> {
        return unwrap {
            service.getCollectionDetail(collectionId, request.buildQuery())
        }
    }

    override suspend fun takeCollection(collectionId: String) {
        return unwrap {
            service.takeCollection(collectionId)
        }
    }


}