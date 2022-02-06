package br.com.data.apiSource

import br.com.data.DataApplication
import br.com.data.apiSource.network.utils.result
import br.com.data.apiSource.services.GithubGistService
import br.com.data.servicesModules
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals

class GithubGistServiceNetworkTest : KoinTest {

    private val githubGistService : GithubGistService by inject()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules( servicesModules(DataApplication.GITHUB_GIST, "") )
    }

    @Test
    fun test_getGist_per_page_parameter() = runBlocking {
        val perPage = 2
        val response =  githubGistService.getGists(perPage = perPage)

        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        response.result(
            success = { gists ->
                assertEquals(perPage, gists.size)
            },
            error =  { AssertionError(it) }
        )
    }
}