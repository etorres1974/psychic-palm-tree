package br.com.data

import br.com.MockGithubGistService
import br.com.data.localSource.entity.GistFilter
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
        gistRepository.queryGistAndSave(1,5, GistFilter.All)
    }

    @Test
    fun repository_fetch_api_and_save_gists_in_db() = runBlocking {
        assertCleanInitialState()

        val gists = gistRepository.getGists()
        assert(gists.isNotEmpty())
            {"Repository gists should not be empty : $gists"}
    }

    @Test
    fun repository_fetch_api_and_save_files_in_db() = runBlocking {
        assertCleanInitialState()

        val files = gistRepository.getFiles()
        assert(files.isNotEmpty())
            {"Repository files should not be empty : $files"}
    }

    @Test
    fun filter_files_by_owner_id() = runBlocking {
        assertCleanInitialState()
        val ownerId = gistRepository.getGists().first().owner_id

        val files = gistRepository.getFilesByOwnerId(ownerId = ownerId)
        assert(files.isNotEmpty())
            {"Repository files should not be empty : $files"}

        assert(files.all{ it.owner_id == ownerId})
            {"Repository files should not be empty : $files"}

    }

    @Test
    fun favorite_gist_by_id() = runBlocking {
        assertCleanInitialState()

        val gists = gistRepository.getGists()
        assert(!gists.all {  it.favorite })
            {"Test initial state should be all gists favorite false"}

        gists.forEach {
            gistRepository.favoriteGist(true, it.id)
        }

        val afteFavorite = gistRepository.getGists().all {it.favorite}
        assert(afteFavorite)
            {"Test final state should be all gists favorite TRUE $afteFavorite"}

    }


    @Test
    fun query_filter_by_favorite_gists() = runBlocking {
        assertCleanInitialState()

        val favoriteGists = gistRepository.getFavoriteGists()
        assert(favoriteGists.isEmpty())
            {" Initial sate should have 0 favorites, but got${favoriteGists.size}"}

        val gists = gistRepository.getGists()
        gistRepository.favoriteGist(true, gists.first().id)
        gistRepository.favoriteGist(true, gists.last().id)

        val afteFavorite = gistRepository.getFavoriteGists()
        assertEquals(2, afteFavorite.count { it.favorite  })
        assertEquals(2, afteFavorite.size)

    }


}