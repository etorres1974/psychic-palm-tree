package br.com.data.paging

import androidx.lifecycle.LiveData
import androidx.paging.*
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.GistFilter
import br.com.data.repository.GistRemoteMediator
import br.com.data.repository.GistRemoteMediator.Companion.PAGE_SIZE
import br.com.data.repository.GistRepository

@OptIn(ExperimentalPagingApi::class)
class GistPages(
    private val gistRepository: GistRepository
) {

   fun getPagedSearchResults(gistFilter: GistFilter): LiveData<PagingData<Gist>>  =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = GistRemoteMediator(gistRepository = gistRepository, filter = gistFilter),
            pagingSourceFactory = {
                 when(gistFilter){
                    GistFilter.All -> gistRepository.gistDao.pagingSource()
                    GistFilter.Favorites -> gistRepository.gistDao.pagingSourceFavorites()
                }
            }
        ).liveData

}