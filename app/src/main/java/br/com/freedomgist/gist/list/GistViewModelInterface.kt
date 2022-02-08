package br.com.freedomgist.gist.list

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.GistFilter
import br.com.freedomgist.gist.GistActions

interface GistViewModelInterface : GistActions {
    fun gisPagestLivedata(gistFilter : GistFilter) : LiveData<PagingData<Gist>>
    val openGist : LiveData<String>
}