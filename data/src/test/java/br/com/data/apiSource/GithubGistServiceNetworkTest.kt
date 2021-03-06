package br.com.data.apiSource

import br.com.data.DataApplication
import br.com.data.apiSource.network.utils.findPageNumbers
import br.com.data.apiSource.network.utils.findPageValueInString
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
        val response =  githubGistService.getGists(page= 1, perPage = perPage)

        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        response.result(
            success = { gists ->
                assertEquals(perPage, gists.size)
            },
            error =  { AssertionError(it) }
        )
    }

    @Test
    fun parse_next_page_from_header() = runBlocking {
        val perPage = 2
        val response =  githubGistService.getGists(perPage = perPage, page= 1)

        assert(response.isSuccessful)
        {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        val header = response.headers()
        val pages = header.findPageNumbers()
        assertEquals(2, pages?.first())
        assertEquals(1500, pages?.last())

    }

    @Test
    fun test_next_page_regex(){
        val link = "<https://api.github.com/search/code?q=addClass+user%3Amozilla&page=15>; rel=\"next\", <https://api.github.com/search/code?q=addClass+user%3Amozilla&page=34>; rel=\"last\""
        val next = link.split(",").first()
        val last = link.split(",").last()
        assertEquals(15, next.findPageValueInString())
        assertEquals(34, last.findPageValueInString())
    }

    @Test
    fun gist_details_has_file_content() = runBlocking {
        val gistIdExample = "8d1aa28c0da3faf260b5dbaf8f9eadab"
        val response =  githubGistService.getGistDetail(gistIdExample)

        assert(response.isSuccessful)
            {"Was expecting 200 but got : ${response.code()} - ${response.errorBody()}"}

        val gist = response.body()
        val filesHaveContent = gist?.files?.list?.all { it.content != null } ?: false

        assert(filesHaveContent) {" Filhes should have content :${gist?.files }"}
    }
}