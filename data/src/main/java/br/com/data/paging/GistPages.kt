package br.com.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.*
import br.com.data.apiSource.services.GithubGistService
import br.com.data.localSource.dao.GistDao
import br.com.data.localSource.entity.Gist
import br.com.data.repository.GistRemoteMediator
import br.com.data.repository.GistRemoteMediator.Companion.PAGE_SIZE
import br.com.data.repository.GistRepository

class GistPages(
    private val gistRepository: GistRepository
) {

    @ExperimentalPagingApi
    fun getPagedSearchResults(query: String): LiveData<PagingData<Gist>>  =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = GistRemoteMediator(gistRepository = gistRepository, query = query),
            pagingSourceFactory = {
                gistRepository.gistDao.pagingSource()
            }
        ).liveData

}