package br.com.data.repository

import android.util.Log
import br.com.data.apiSource.models.AccessToken
import br.com.data.apiSource.models.DeviceCode
import br.com.data.apiSource.network.utils.*
import br.com.data.apiSource.services.Github
import br.com.data.localSource.dao.AuthDao
import br.com.data.localSource.entity.Auth
import java.lang.Exception

class AuthRepository(
    private val github: Github,
    private val authDao: AuthDao
) {

    suspend fun fetchUserCode() : NetworkResult<DeviceCode?> = try{
        github.getDeviceCode().handleResponse()
    }catch (e : Exception){
        Log.d("ABACATE ", e.toString())
        NetworkResult.Error(ErrorEntity.Unknown)
    }

    suspend fun fetchUserToken(deviceCode: DeviceCode) {
        github.getToken(deviceCode = deviceCode.device_code).result(
            success = { saveUserToken(it) },
            error =  { // TODO
                throw NotImplementedError("$it")
            }
        )
    }

    private fun saveUserToken(accessToken: AccessToken) {
        val auth = Auth(token = accessToken.access_token, type = accessToken.token_type)
        authDao.insert(auth)
        AUTH.value = auth
    }
}