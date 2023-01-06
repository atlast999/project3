package com.example.webtoapp.base.serialize

import com.google.gson.GsonBuilder

interface Serializer {
    fun <T> deserialize(raw: String, clazz: Class<T>): T
    fun <T> serialize(value: T): String
}

inline fun <reified T> Serializer.deserialize(raw: String) = deserialize(raw, T::class.java)

class GsonSerializer : Serializer {

    private val gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    override fun <T> deserialize(raw: String, clazz: Class<T>): T {
        return gson.fromJson(raw, clazz)
    }

    override fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

}