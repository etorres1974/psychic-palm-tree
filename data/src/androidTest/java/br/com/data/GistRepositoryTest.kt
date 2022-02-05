package br.com.data

import br.com.MockGithubGistService
import br.com.data.repository.GistRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GistRepositoryTest  : BaseTest(){

    val gistRepository : GistRepository by lazy { app.gistRepository }

    @Test
    fun repositry_fetch_api_and_save_gists_in_db() = runBlocking {
        MockGithubGistService().successApi()
        assert(gistRepository.getGists().isEmpty())

        gistRepository.queryGistAndSave()

        val gists = gistRepository.getGists()
        assert(gists.isNotEmpty())
            {"Repository gists should not be empty : $gists"}
    }

    @Test
    fun repositry_fetch_api_and_save_files_in_db() = runBlocking {
        MockGithubGistService().successApi()
        assert(gistRepository.getFiles().isEmpty())

        gistRepository.queryGistAndSave()

        val files = gistRepository.getFiles()
        assert(files.isNotEmpty())
        {"Repository files should not be empty : $files"}

    }
}