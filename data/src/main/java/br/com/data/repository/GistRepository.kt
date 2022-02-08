package br.com.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import br.com.data.apiSource.models.GistDTO
import br.com.data.apiSource.network.utils.NetworkResult
import br.com.data.apiSource.network.utils.handleResponse
import br.com.data.apiSource.services.GithubGistService
import br.com.data.localSource.GistDatabase
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.GistFilter
import br.com.data.paging.GistPages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class GistRepository(
    private val githubGistService: GithubGistService,
    gistDatabase: GistDatabase
) {

    private val fileDao = gistDatabase.fileDao()
    val gistDao = gistDatabase.gistDao()

    fun getPagedGists(gistFilter: GistFilter) : LiveData<PagingData<Gist>> =
        GistPages(this).getPagedSearchResults(gistFilter)

    fun getGists() = gistDao.getAll()

    fun getFavoriteGists() = gistDao.getFavorites()

    fun getGistById(gistId : String) = gistDao.getByid(gistId)

    fun getFiles() = fileDao.getAll()
    fun getFilesByOwnerId( ownerId : Int) = fileDao.getByOwnerId(ownerId)

    suspend fun favoriteGist(favorite : Boolean ,  gistId : String) = gistDao.favorite(gistId, !favorite)

    suspend fun updateGistDetails(gistId : String): NetworkResult<GistDTO> {
        try {
            val res = githubGistService.getGistDetail(gistId)
            val response = res.handleResponse()
            Log.d("ABACATE", "Details Response = ${response}")
            if(response is NetworkResult.Success)
                updateGistDetails(response.data)
            return response
        } catch (e :Exception) {
            throw e
        }
    }

    suspend fun queryGistAndSave(page: Int, perPage: Int, filter: GistFilter): Response<List<GistDTO>> {
            try {
                val res = githubGistService.getGists(page = page, perPage = perPage)
                val response = res.handleResponse()
                if (response is NetworkResult.Success)
                    response.data.forEach { saveRemoteGist(it, page) }
                return res
            }catch (e : Exception){
                throw e
            }
        }

        private suspend fun saveRemoteGist(gist : GistDTO, page : Int) = withContext(Dispatchers.IO){
            val gistDb = gist.toDbModel(page)
            val files = gist.getFilesDb()
            gistDao.insert(gistDb)
            files.forEach {  file ->
                fileDao.insert(file)
            }
        }

        private suspend fun updateGistDetails(gist : GistDTO) = withContext(Dispatchers.IO){
            gist.getFilesDb().forEach { file ->
                fileDao.update(file)
            }
        }
}