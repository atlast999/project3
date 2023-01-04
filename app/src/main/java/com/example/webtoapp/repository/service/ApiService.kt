package com.example.webtoapp.repository.service

import com.example.webtoapp.base.domain.BaseResponse
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.model.*
import retrofit2.http.*

interface ApiService {
    @POST("/signup")
    suspend fun signup(@Body request: AuthenticationRequest): BaseResponse<AuthenticationResponse>

    @POST("/login")
    suspend fun login(@Body request: AuthenticationRequest): BaseResponse<AuthenticationResponse>

    @POST("/web_apps")
    suspend fun createWebApp(@Body request: WebAppInstance): BaseResponse<Unit>

    @GET("/web_apps")
    suspend fun getMyList(@QueryMap params: Map<String, String>): BaseResponse<PagingModel<WebAppInstance>>

    @POST("share_list")
    suspend fun shareMyList(@Body request: ShareMyWebAppListRequest): BaseResponse<Unit>

    @GET("collections")
    suspend fun getCollectionList(@QueryMap params: Map<String, String>): BaseResponse<PagingModel<AppCollection>>

    @GET("collections/{id}")
    suspend fun getCollectionDetail(
        @Path("id") collectionId: String,
        @QueryMap params: Map<String, String>
    ): BaseResponse<PagingModel<WebAppInstance>>

    @PUT("collections/{id}/take")
    suspend fun takeCollection(@Path("id") collectionId: String): BaseResponse<Unit>
}