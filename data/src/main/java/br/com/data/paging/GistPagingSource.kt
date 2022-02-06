package br.com.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.data.apiSource.network.utils.handleResponse
import br.com.data.apiSource.network.utils.result
import br.com.data.apiSource.services.GithubGistService
import br.com.data.localSource.entity.Gist
import retrofit2.HttpException
import java.io.IOException


class GistPagingSource(
    private val service: GithubGistService,
    private val query: String
) : PagingSource<Int, Gist>() {

    override fun getRefreshKey(state: PagingState<Int, Gist>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gist> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query //TODO - USE QUERY FOR USERS
        return try {
            var gists : List<Gist> = emptyList()
            
            service.getGists(perPage = PAGE_SIZE, page = position).result(
                success = { res -> gists = res.map { it.toDbModel() } },
                error =  { }
            )

            LoadResult.Page(
                data = gists,
                prevKey = resolvePrevKey(position),
                nextKey = gists.resolveNexKey(position,params)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    fun List<Gist>.resolveNexKey(position : Int, params :  LoadParams<Int> ) = if (isEmpty()) {
        null
    } else {
        position + (params.loadSize / PAGE_SIZE )
    }

    fun resolvePrevKey(position : Int ) = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1

    companion object{
         const val GITHUB_STARTING_PAGE_INDEX = 1
         const val PAGE_SIZE = 10
    }
}