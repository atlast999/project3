package com.example.webtoapp.base.serialize

import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface Serializer {
    fun <T> deserialize(raw: String, clazz: Class<T>): T
    fun <T> serialize(value: T): String
}

inline fun <reified T> Serializer.deserialize(raw: String) = deserialize(raw, T::class.java)

class GsonSerializer : Serializer {

    private val gson = provideGson()

    override fun <T> deserialize(raw: String, clazz: Class<T>): T {
        return gson.fromJson(raw, clazz)
    }

    override fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

    companion object {
        fun provideGson(): Gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

}