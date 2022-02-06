package br.com.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.data.apiSource.GithubGistService
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
            val response = service.getGists(perPage = PAGE_SIZE, page = position)
            val gists = response.body() ?: emptyList()

            val nextKey = if (gists.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE )
            }

            val prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1
            LoadResult.Page(
                data = gists.map { it.toDbModel() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    companion object{
         const val GITHUB_STARTING_PAGE_INDEX = 1
         const val PAGE_SIZE = 10
    }
}