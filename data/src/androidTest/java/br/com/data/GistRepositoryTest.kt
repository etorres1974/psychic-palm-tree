package br.com.data

import br.com.MockGithubGistService
import br.com.data.repository.GistRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GistRepositoryTest  : InstrumentedTest() {

    val gistRepository : GistRepository by lazy { app.gistRepository }

    @Before
    override fun setup() {
        super.setup()
        mockWebServer = MockGithubGistService().successApi()
    }

    fun assertCleanInitialState() = runBlocking{
        assert(gistRepository.getGists().isEmpty())
        {"Test initial state should have 0 Gists  but got ${gistRepository.getGists().size}"}
        gistRepository.queryGistAndSave()
    }

    @Test
    fun repositry_fetch_api_and_save_gists_in_db() = runBlocking {
        assertCleanInitialState()

        val gists = gistRepository.getGists()
        assert(gists.isNotEmpty())
            {"Repository gists should not be empty : $gists"}
    }

    @Test
    fun repositry_fetch_api_and_save_files_in_db() = runBlocking {
        assertCleanInitialState()

        val files = gistRepository.getFiles()
        assert(files.isNotEmpty())
        {"Repository files should not be empty : $files"}
    }

    @Test
    fun favorite_gist_by_id() = runBlocking {
        assertCleanInitialState()

        val gists = gistRepository.getGists()
        assert(!gists.all {  it.favorite })
            {"Test initial state should be all gists favorite false"}

        gists.forEach {
            gistRepository.favoriteGist(it.id)
        }

        val afteFavorite = gistRepository.getGists().all {it.favorite}
        assert(afteFavorite)
            {"Test final state should be all gists favorite TRUE $afteFavorite"}

        gists.first().let {
            gistRepository.unFavoriteGist(it.id)
        }

        val afteUnfavorite = gistRepository.getGists()
        assert(!afteUnfavorite[0].favorite)
        assert(afteUnfavorite[1].favorite)
        assert(afteUnfavorite[2].favorite)
    }


    @Test
    fun query_filter_by_favorite_gists() = runBlocking {
        assertCleanInitialState()

        val favoriteGists = gistRepository.getFavoriteGists()
        assert(favoriteGists.isEmpty())
            {" Initial sate should have 0 favorites, but got${favoriteGists.size}"}

        val gists = gistRepository.getGists()
        gistRepository.favoriteGist(gists.first().id)
        gistRepository.favoriteGist(gists.last().id)

        val afteFavorite = gistRepository.getFavoriteGists()
        assertEquals(2, afteFavorite.count { it.favorite  })
        assertEquals(2, afteFavorite.size)

    }


}