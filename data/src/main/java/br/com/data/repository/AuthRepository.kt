package br.com.data.repository

import android.util.Log
import br.com.data.apiSource.models.AccessToken
import br.com.data.apiSource.models.DeviceCode
import br.com.data.apiSource.network.utils.*
import br.com.data.apiSource.services.Github
import br.com.data.localSource.dao.AuthDao
import br.com.data.localSource.entity.Auth
import java.net.UnknownHostException

class AuthRepository(
    private val github: Github,
    private val authDao: AuthDao
) {

    suspend fun fetchUserCode() : NetworkResult<DeviceCode> = try{
        val response = github.getDeviceCode().handleResponse()
        if(response is NetworkResult.Success)
            AUTH.deviceCode = response.data
        response
    }catch (e : Exception){
        when (e) {
            is UnknownHostException -> NetworkResult.Error(ErrorEntity.NetworkError)
            else -> {
                Log.d("ABACATE", "Fetch UC  ${e}")
                NetworkResult.Error(ErrorEntity.Unknown)
            }
        }
    }

    suspend fun fetchUserToken(deviceCode: DeviceCode) {
        github.getToken(deviceCode = deviceCode.device_code).result(
            success = { saveUserToken(it) },
            error =  { // TODO
                Log.d("ABACATE", "Fail to fetch token ${it}")
            }
        )
    }

    private fun saveUserToken(accessToken: AccessToken) {
        Log.d("ABACATE", "New token Save ${accessToken}")
        val auth = Auth(token = accessToken.access_token, type = accessToken.token_type)
        authDao.insert(auth)
        AUTH.token = auth
    }
}