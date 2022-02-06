package br.com.freedomgist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import br.com.data.apiSource.models.DeviceCode
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.data.apiSource.network.utils.handleResult
import br.com.data.localSource.entity.Gist
import br.com.data.repository.AuthRepository
import br.com.data.repository.GistRepository
import br.com.freedomgist.gist.GistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainViewModel(
    private val gistRepository: GistRepository,
    private val authRepository: AuthRepository
) : ViewModel(), GistViewModel {


    val pagedGist : LiveData<PagingData<Gist>> = queryPagedGists()

    override fun gisPagestLivedata(): LiveData<PagingData<Gist>>  = pagedGist

    private fun queryPagedGists(query : String = "") = gistRepository.getPagedGists(query)

    override val openGist: LiveData<String>
        get() = TODO("Not yet implemented")

    override fun onClickGist(id: String) {
        TODO("Not yet implemented")
    }

    val userCodeLiveData = MutableLiveData<DeviceCode>()
    val errorEntityLiveData = MutableLiveData<ErrorEntity>()

    fun askPermissionCode() = viewModelScope.launch(Dispatchers.IO) {
        authRepository.fetchUserCode().handleResult(
            success = {  userCodeLiveData.postValue(it) },
            error = { error -> errorEntityLiveData.postValue(error) }
        )
    }
}


