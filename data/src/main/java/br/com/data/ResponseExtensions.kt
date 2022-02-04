package br.com.data

import retrofit2.Response
import java.lang.Exception

fun <T> Response<T>.handleResponse( ): T?  = try {
    body()
}catch (e : Exception){
    throw e
}
