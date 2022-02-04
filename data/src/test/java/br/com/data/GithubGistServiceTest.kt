package br.com.data

import kotlinx.coroutines.runBlocking
import org.junit.Test

class GithubGistServiceTest {

    private val githubGistService = HttpClient().gitHubGistService()

    @Test
    fun desserealizeGistsEntitiesCorrectly() = runBlocking {
        val response =  githubGistService.getGists()
        assert(response.isSuccessful) {" Request falhou : ${response.code()} - ${response.errorBody()}"}
        val gists = response.handleResponse()
        assert(gists!!.isNotEmpty())
            {"Fail to parse GISTS : $gists"}

        val files = gists.map { it.files }.flatMap { it.list }
        assert(files.isNotEmpty())
            {" Fail to parse FILES : $files"}

        val owners = gists.map { it.owner }
        assert(files.isNotEmpty())
            {" Fail to parse OWNERS  : $owners"}
    }
}