package br.com.freedomgist.gist

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import br.com.data.localSource.entity.Gist

interface GistViewModel {
    fun gisPagestLivedata() : LiveData<PagingData<Gist>>
    val openGist : LiveData<String>
    fun onClickGist(id : String)
}