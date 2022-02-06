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
               getRemoteKeyClosestToCurrentPosition(state)
            }
            LoadType.PREPEND -> {
                val fistPage = firstGist(state)?.page
                val prevKey : Int? = fistPage?.previous()
                if (fistPage  == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val lastPage = lastGist(state)?.page
                val nextKey : Int? = lastPage?.next()
                if (lastPage == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
                nextKey
            }
        } ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query //TODO use query for user
        Log.d("ABACATE", "$loadType - $page")
        return try {
            if(page >= GITHUB_STARTING_PAGE_INDEX) {
                val res = gistRepository.queryGistAndSave(page = page)
            }
            MediatorResult.Success(endOfPaginationReached = false)//TODO checkEnd
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private fun Int.previous() = this -1

    fun Int.next() = this + 1

    private fun firstGist(state: PagingState<Int, Gist>) : Gist? =
         state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()

    private fun lastGist(state: PagingState<Int, Gist>) : Gist? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()

    private fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Gist>
    ): Int= state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.page
        } ?: GITHUB_STARTING_PAGE_INDEX
    companion object{
        const val GITHUB_STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }

}