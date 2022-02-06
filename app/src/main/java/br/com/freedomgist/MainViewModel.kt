package br.com.freedomgist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import br.com.data.localSource.entity.Gist
import br.com.data.repository.GistRepository
import br.com.freedomgist.gist.GistViewModel

class MainViewModel(
    private val gistRepository: GistRepository
) : ViewModel(), GistViewModel {

    val pagedGist : LiveData<PagingData<Gist>> = queryPagedGists()

    override fun gisPagestLivedata(): LiveData<PagingData<Gist>>  = pagedGist

    private fun queryPagedGists(query : String = "") = gistRepository.getPagedGists(query)

    override val openGist: LiveData<String>
        get() = TODO("Not yet implemented")

    override fun onClickGist(id: String) {
        TODO("Not yet implemented")
    }
}

