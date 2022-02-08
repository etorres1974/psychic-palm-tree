package br.com.freedomgist.gist.list

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.GistFilter

interface GistViewModel {
    fun gisPagestLivedata(gistFilter : GistFilter) : LiveData<PagingData<Gist>>
    val openGist : LiveData<Int>
    fun onClickGist(id : Int) : Unit
    fun onFavoriteGist(favorite : Boolean, id : String) : Unit
}