package br.com.freedomgist.gist.file

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import br.com.data.apiSource.network.utils.getTextFromWeb
import br.com.data.localSource.entity.File
import br.com.data.repository.GistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagingApi::class)
class FileViewModel(
    private val gistRepository: GistRepository,
) : ViewModel() {

    private val _files = MutableLiveData<List<File>>()
    val files : LiveData<List<File>> = _files

    private val _code = MutableLiveData<String>()
    val code : LiveData<String> = _code

    fun getFiles(ownerId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _files.postValue(gistRepository.getFilesByOwnerId(ownerId))
        }
    }

    fun getCode(url : String){
        viewModelScope.launch(Dispatchers.IO) {
            _code.postValue(getTextFromWeb(url))
        }
    }

}


