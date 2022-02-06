package br.com.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import br.com.data.apiSource.services.GithubGistService
import br.com.data.apiSource.models.GistDTO
import br.com.data.apiSource.network.utils.result
import br.com.data.localSource.GistDatabase
import br.com.data.localSource.entity.Gist
import br.com.data.paging.GistPages

class GistRepository(
    private val githubGistService: GithubGistService,
    gistDatabase: GistDatabase
) {

    private val fileDao = gistDatabase.fileDao()
    private val gistDao = gistDatabase.gistDao()

    fun getPagedGists(query : String) : LiveData<PagingData<Gist>> =
        GistPages(githubGistService).getPagedSearchResults(query)

    fun getGists() = gistDao.getAll()

    fun getFavoriteGists() = gistDao.getFavorites()

    fun getFiles() = fileDao.getAll()
    fun getFilesByOwnerId( ownerId : Int) = fileDao.getByOwnerId(ownerId)

    fun favoriteGist( gistId : String) = gistDao.favorite(gistId, true)
    fun unFavoriteGist( gistId : String) = gistDao.favorite(gistId, false)

    suspend fun queryGistAndSave(){
        githubGistService.getGists().result(
            success = { gists ->
                gists.forEach{ saveRemoteGist(it ) }
            },
            error = { err ->
                Log.e("GistRepository", "Query Gist and Save : $err")
            }
        )
    }

    private fun saveRemoteGist(gist : GistDTO){
        val gistDb = gist.toDbModel()
        val files = gist.getFilesDb()
        gistDao.insert(gistDb)
        fileDao.insert(files)
    }
}