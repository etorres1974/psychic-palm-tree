package br.com.data.apiSource.network.utils

import okhttp3.Headers

private val pageRegex = Regex("&page=[\\d]*")
fun CharSequence.findPageValueInString() : Int? = pageRegex.find(this)?.value?.split("&page=")?.last()?.toInt()

fun Headers.findPageNumbers() = get("link")?.split(",")?.map { it.findPageValueInString() ?: 1 }?.sorted()