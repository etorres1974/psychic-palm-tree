package br.com.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.data.localSource.entity.Gist
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class GistRemoteMediator(
    private val gistRepository: GistRepository,
    private val query : String = ""
) : RemoteMediator<Int, Gist>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Gist>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
               val current = getRemoteKeyClosestToCurrentPosition(state)
               current ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val fistPage = firstGist(state)?.page
                val prevKey : Int= fistPage?.let{ it - 1 } ?: 0
                if (fistPage  == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val lastPage = lastGist(state)?.page
                val nextKey : Int= lastPage?.let{ it + 1 } ?: 0
                if (lastPage == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                nextKey
            }
        }
        val apiQuery = query //TODO use query for user
        return try {
            val apiResponse = gistRepository.queryGistAndSave(page = page)
            MediatorResult.Success(endOfPaginationReached = false)//TODO checkEnd
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private fun firstGist(state: PagingState<Int, Gist>) : Gist? =
         state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()

    private fun lastGist(state: PagingState<Int, Gist>) : Gist? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Gist>
    ): Int? = state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.page
        }
    companion object{
        const val GITHUB_STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }

}