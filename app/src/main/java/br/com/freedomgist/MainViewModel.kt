package br.com.freedomgist

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import br.com.data.apiSource.models.DeviceCode
import br.com.data.apiSource.network.utils.ErrorEntity
import br.com.data.apiSource.network.utils.handleResult
import br.com.data.localSource.entity.Gist
import br.com.data.repository.AuthRepository
import br.com.data.repository.GistRepository
import br.com.data.localSource.entity.GistFilter
import br.com.freedomgist.gist.list.GistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
class MainViewModel(
    private val gistRepository: GistRepository,
    private val authRepository: AuthRepository
) : ViewModel(), GistViewModel {

    override fun gisPagestLivedata(gistFilter: GistFilter): LiveData<PagingData<Gist>> = gistRepository.getPagedGists(gistFilter)

    override val openGist: LiveData<String>
        get() = TODO("Not yet implemented")

    override fun onClickGist(id: String) {
        //TODO - Open Files
        Log.d("ABACATE", "Clicked  Gist : ${id}")
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


