package br.com.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import br.com.data.apiSource.network.utils.findPageNumbers
import br.com.data.localSource.entity.Gist
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class GistRemoteMediator(
    private val gistRepository: GistRepository,
    private val query: String = ""
) : RemoteMediator<Int, Gist>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Gist>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> GITHUB_STARTING_PAGE_INDEX
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastPage = state.lastItemOrNull()?.page
                val nextKey: Int? = lastPage?.next()
                if(nextKey == null)
                    return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }
        val apiQuery = query //TODO use query for user
        return try {
            val res = gistRepository.queryGistAndSave(page = page, perPage = PAGE_SIZE)
            val lastPage = res.headers().findPageNumbers()?.last() ?: -1
            val endOfPaginationReached = page >= lastPage
            if (res.isSuccessful)
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            else
                MediatorResult.Error(IllegalAccessException())
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    fun Int.next() = this + 1

    companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 30
    }

}