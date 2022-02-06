package br.com.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import br.com.data.apiSource.GithubGistService
import br.com.data.localSource.entity.Gist
import br.com.data.paging.GistPagingSource.Companion.PAGE_SIZE

class GistPages(
    private val service: GithubGistService
) {

    fun getPagedSearchResults(query: String): LiveData<PagingData<Gist>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GistPagingSource(service, query)
            }
        ).liveData
    }
}