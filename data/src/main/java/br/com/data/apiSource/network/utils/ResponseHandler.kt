package br.com.data.apiSource.network.utils

import android.util.Log
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.Exception
import kotlin.Unit
import kotlin.let

sealed class ErrorEntity {

    object ValidationError : ErrorEntity()

    object NetworkError : ErrorEntity()

    object Forbidden : ErrorEntity()

    object Unknown : ErrorEntity()
}

sealed class NetworkResult<T> {

    data class Success<T>(val data: T) : NetworkResult<T>()

    data class Error<T>(val errorEntity: ErrorEntity) : NetworkResult<T>()
}

fun <T> Response<T>.handleResponse( ): NetworkResult<T> = try {
    body()?.let {
        NetworkResult.Success(it)
    } ?: when {
        this.code() == 403 -> NetworkResult.Error(ErrorEntity.Forbidden)
        this.code() == 422 -> NetworkResult.Error(ErrorEntity.ValidationError)
        else -> NetworkResult.Error(ErrorEntity.ValidationError)
    }
}catch (e : Exception){
    when (e) {
        is UnknownHostException -> NetworkResult.Error(ErrorEntity.NetworkError)
        else -> {
            Log.d("ABACATE", "handle Res : ${e}")
            NetworkResult.Error(ErrorEntity.Unknown)
        }
    }

}

fun <T> NetworkResult<T>.handleResult(
    success : (T) -> Unit,
    error : (ErrorEntity) -> Unit
) = try {
    when(this){
        is NetworkResult.Success<T> -> success(data)
        is NetworkResult.Error<T> -> error(errorEntity)
    }
}catch (e : Exception){
    Log.d("ABACATE", "handleResult ${e}")
     error(ErrorEntity.Unknown)
}

fun <T> Response<T>.result(
    success : (T) -> Unit,
    error : (ErrorEntity) -> Unit
) = handleResponse().handleResult(success, error)
