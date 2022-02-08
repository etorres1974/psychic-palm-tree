package br.com.data.apiSource.services

import br.com.data.BuildConfig
import br.com.data.apiSource.models.AccessToken
import br.com.data.apiSource.models.DeviceCode
import retrofit2.http.*
import retrofit2.Response


interface Github : Api {

    @POST("/login/device/code")
    suspend fun getDeviceCode(
        @Query("client_id") clientId : String = BuildConfig.github_client_id
    ) : Response<DeviceCode>


    @POST("/login/oauth/access_token")
    suspend fun getToken(
        @Query("client_id") clientId : String = BuildConfig.github_client_id,
        @Query("device_code") deviceCode : String,
        @Query("grant_type") grantType : String = "urn:ietf:params:oauth:grant-type:device_code"
    ) : Response<AccessToken>
}