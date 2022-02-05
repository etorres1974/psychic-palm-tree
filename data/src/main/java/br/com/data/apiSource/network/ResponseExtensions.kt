package br.com.data.apiSource.network

import retrofit2.Response
import kotlin.Exception
import kotlin.Unit
import kotlin.let

sealed class ErrorEntity {

    object ValidationError : ErrorEntity()

    object Unknown : ErrorEntity()
}

private sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val errorEntity: ErrorEntity) : Result<T>()
}

private fun <T> Response<T>.handleResponse( ): Result<T> = try {
    body()?.let {
        Result.Success(it)
    } ?: when {
        this.code() == 422 -> Result.Error(ErrorEntity.ValidationError)
        else -> Result.Error(ErrorEntity.Unknown)
    }
}catch (e : Exception){
    Result.Error(ErrorEntity.Unknown)
}

private fun <T> Result<T>.handleResult(
    success : (T) -> Unit,
    error : (ErrorEntity) -> Unit
) = try {
    when(this){
        is Result.Success<T> -> success(data)
        is Result.Error<T> -> error(errorEntity)
    }
}catch (e : Exception){
     error(ErrorEntity.Unknown)
}

fun <T> Response<T>.result(
    success : (T) -> Unit,
    error : (ErrorEntity) -> Unit
) = handleResponse().handleResult(success, error)
