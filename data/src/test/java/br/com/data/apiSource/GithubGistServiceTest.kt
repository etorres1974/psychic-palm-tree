package br.com.data.apiSource

import br.com.DataTestRunner
import br.com.MockGithubGistService
import br.com.MockWebServerTest
import br.com.data.apiSource.network.ErrorEntity
import br.com.data.apiSource.network.result
import br.com.data.servicesModules
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class GithubGistServiceTest : MockWebServerTest() , KoinTest {

    private val githubGistService : GithubGistService by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules( servicesModules(DataTestRunner.MOCK_BASE_URL) )
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