package br.com.data

import kotlinx.coroutines.runBlocking
import org.junit.Test

class GithubGistServiceTest {

    private lateinit var githubGistService : GithubGistService

    @Test
    fun desserealizeGistsEntitiesCorrectly() = runBlocking {
        githubGistService = HttpClient().gitHubGistService()
        val response =  githubGistService.getGists()
        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        val gists = response.handleResponse()
        assert(gists!!.isNotEmpty())
            {"Fail to parse GISTS : $gists"}

        val files = gists.map { it.files }.flatMap { it.list }
        assert(files.isNotEmpty())
            {"Fail to parse FILES : $files"}

        val owners = gists.map { it.owner }
        assert(files.isNotEmpty())
            {"Fail to parse OWNERS  : $owners"}
    }

    @Test
    fun success_mock_api_returns_single_Gist_Json() = runBlocking {
        githubGistService = MockGithubGistService().successApi()
        val response = githubGistService.getGists()
        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}
        val gists = response.handleResponse()
        assert(gists!!.size == 1)
            {"Mocked Gist response should have only one Gist: ${gists.size}"}
    }

    @Test
    fun error_mock_api_returns_422() = runBlocking {
        githubGistService = MockGithubGistService().errorApi()
        val response = githubGistService.getGists()
        assert(response.code() == 422)
            {" Was expecting 422 but received : ${response.code()} - ${response.errorBody()}"}
        val data = response.handleResponse()
        assert(data == null)
            {" Was expecting Null but received : $data"}
    }
}