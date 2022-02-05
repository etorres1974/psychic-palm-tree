package br.com.data.apiSource

import br.com.MockGithubGistService
import br.com.DataTestRunner
import br.com.data.apiSource.network.ErrorEntity
import br.com.data.apiSource.network.HttpClient
import br.com.data.apiSource.network.result
import br.com.data.applicationModules
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.lang.AssertionError

class GithubGistServiceTest : KoinTest {

    private val httpClient : HttpClient by inject()
    private lateinit var githubGistService : GithubGistService
    private lateinit var mockWebServer : MockWebServer

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules( applicationModules(DataTestRunner.MOCK_BASE_URL) )
    }

    @Before
    fun setup(){
        githubGistService  = httpClient.gitHubGistService()
    }

    @After
    fun teardown(){
        mockWebServer.dispatcher.shutdown()
        mockWebServer.shutdown()
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
}