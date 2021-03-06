package br.com.data.apiSource

import br.com.DataTestRunner
import br.com.MockGithubGistService
import br.com.MockWebServerTest
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.data.apiSource.network.utils.JsonHelper
import br.com.data.apiSource.network.utils.result
import br.com.data.apiSource.services.GithubGistService
import br.com.data.servicesModules
import br.com.fileReader
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals

class GithubGistServiceTest : MockWebServerTest() , KoinTest {

    private val githubGistService : GithubGistService by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules( servicesModules(DataTestRunner.MOCK_BASE_URL, DataTestRunner.MOCK_BASE_URL) )
    }

    @Test
    fun deserealize_gists_entities_correctly() = runBlocking {
        mockWebServer = MockGithubGistService().successApi()
        val response =  githubGistService.getGists()

        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        response.result(
            success = { gists ->
                assert(gists.isNotEmpty())
                {"Fail to parse GISTS : $gists"}

                val files = gists.map { it.files }.flatMap { it.list }
                assert(files.isNotEmpty())
                {"Fail to parse FILES : $files"}

                val owners = gists.map { it.owner }
                assert(files.isNotEmpty())
                {"Fail to parse OWNERS  : $owners"}
            },
            error =  { AssertionError(it) }
        )
    }

    @Test
    fun success_mock_api_returns_Multiple_Gist_Json() = runBlocking {
        mockWebServer = MockGithubGistService().successApi()
        val response = githubGistService.getGists()

        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        response.result(
            success = { gists ->
                assert(gists.size == 3)
                {"Mocked Gist response should have only one Gist: ${gists.size}"}
            },
            error = { AssertionError(it) }
        )
    }

    @Test
    fun error_mock_api_returns_422() = runBlocking {
        mockWebServer = MockGithubGistService().errorApi()
        val response = githubGistService.getGists()

        assert(response.code() == 422)
            {" Was expecting 422 but received : ${response.code()} - ${response.errorBody()}"}

        response.result(
            success = {
                assert(false) {"Was expected to return Validation Error bur got : $it "}
            },
            error = {  error ->
                assert(error is ErrorEntity.ValidationError)
                    {"Was expected to return Validation Error but got : $error"}
            }
        )
    }

    @Test
    fun test_unique_ids() = runBlocking{
        val errorSample = fileReader("Large.json")
        mockWebServer = MockWebServer().apply {
            start(8080)
        }
        mockWebServer?.enqueue(MockResponse().setResponseCode(200).setBody(errorSample))
        val response = githubGistService.getGists()
        val gists = response.body()!!
        val ids = gists.map { it.id }
        val keys = gists.map { it.id }.distinct()
        assertEquals(ids, keys)
    }
}