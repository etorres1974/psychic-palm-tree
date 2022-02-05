package br.com.data.repository

import android.util.Log
import br.com.data.apiSource.GithubGistService
import br.com.data.apiSource.models.GistDTO
import br.com.data.apiSource.network.result
import br.com.data.localSource.GistDatabase

class GistRepository(
    private val githubGistService: GithubGistService,
    gistDatabase: GistDatabase
) {

    private val fileDao = gistDatabase.fileDao()
    private val gistDao = gistDatabase.gistDao()

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