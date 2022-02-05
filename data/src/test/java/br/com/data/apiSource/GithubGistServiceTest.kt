package br.com.data.apiSource

import br.com.MockGithubGistService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.AssertionError

class GithubGistServiceTest {

    private lateinit var githubGistService : GithubGistService

    @Test
    fun deserealize_gists_entities_correctly() = runBlocking {
        githubGistService = HttpClient().gitHubGistService()
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
    fun success_mock_api_returns_single_Gist_Json() = runBlocking {
        githubGistService = MockGithubGistService().successApi()
        val response = githubGistService.getGists()

        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        response.result(
            success = { gists ->
                assert(gists.size == 1)
                {"Mocked Gist response should have only one Gist: ${gists.size}"}
            },
            error = { AssertionError(it) }
        )
    }

    @Test
    fun error_mock_api_returns_422() = runBlocking {
        githubGistService = MockGithubGistService().errorApi()
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