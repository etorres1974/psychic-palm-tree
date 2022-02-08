package br.com.freedomgist.gist

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
import br.com.data.localSource.entity.GistFilter
import br.com.data.repository.AuthRepository
import br.com.data.repository.GistRepository
import br.com.freedomgist.gist.list.GistViewModelInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagingApi::class)
class GistViewModel(
    private val gistRepository: GistRepository,
    private val authRepository: AuthRepository
) : ViewModel(), GistViewModelInterface {

    override fun gisPagestLivedata(gistFilter: GistFilter): LiveData<PagingData<Gist>> = gistRepository.getPagedGists(gistFilter)

    private val _openGist = MutableLiveData<String>()
    override val openGist: LiveData<String> = _openGist

    override fun onClickGist(gistId : String){
        viewModelScope.launch(Dispatchers.IO) {
            _openGist.postValue(gistId)
        }
    }

    override fun onClickOwner(ownerId: Int) {
        TODO("Not yet implemented")
    }

    override fun onFavoriteGist(favorite : Boolean, id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gistRepository.favoriteGist(favorite, id)
        }
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


