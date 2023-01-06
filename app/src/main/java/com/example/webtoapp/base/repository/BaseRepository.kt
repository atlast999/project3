package com.example.webtoapp.base.repository

import com.example.webtoapp.base.domain.BaseResponse
import com.example.webtoapp.base.domain.FailedResponse
import com.example.webtoapp.base.exception.NetworkException
import com.example.webtoapp.base.serialize.Serializer
import com.example.webtoapp.base.serialize.deserialize
import retrofit2.HttpException

abstract class BaseRepository(private val serializer: Serializer) {

    suspend fun <T> unwrap(block: suspend () -> BaseResponse<T>): T {
        try {
            return block.invoke().data
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    e.response()?.errorBody()?.string()?.let { raw ->
                        val baseResponse: FailedResponse = serializer.deserialize(raw)
                        throw NetworkException(baseResponse.message)
                    } ?: throw e
                }
                else -> throw e
            }
        }
    }
}