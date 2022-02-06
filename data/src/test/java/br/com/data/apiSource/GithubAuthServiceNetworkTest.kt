package br.com.data.apiSource

import br.com.data.BuildConfig
import br.com.data.DataApplication
import br.com.data.apiSource.services.Github
import br.com.data.servicesModules
import br.com.fileReader
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals

class GithubAuthServiceNetworkTest : KoinTest {

    private val githubService : Github by inject()
    private val clientId = BuildConfig.github_client_id

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules( servicesModules(DataApplication.GITHUB_GIST, DataApplication.GITHUB) )
    }

    @Test
    fun get_device_code() = runBlocking {
        val response =  githubService.getDeviceCode(clientId)
        assertEquals(
            "https://github.com/login/device",
            response.body()?.verification_uri)
    }

    @Test
    fun get_token_fail_pending_auth() = runBlocking {
        val response =  githubService.getDeviceCode(clientId)
        val deviceCode = response.body()!!
        val result = githubService.getToken(
            clientId = clientId,
            deviceCode = deviceCode.device_code
        )
        val errorSample = fileReader("AuthorizationPending.json")
        assertEquals(errorSample, result.errorBody().toString())
    }

}