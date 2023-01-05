package com.example.webtoapp.base.util

fun <R> String?.notNullOrEmptyLet(block: (String) -> R): R? = this?.let {
    return@let if (it.isNotEmpty()) block.invoke(it) else null
}
