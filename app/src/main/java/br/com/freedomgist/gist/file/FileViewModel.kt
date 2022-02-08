package br.com.freedomgist.gist.file

import android.gesture.GestureStore
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import br.com.data.localSource.entity.File
import br.com.data.localSource.entity.GistAndAllFiles
import br.com.data.repository.GistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagingApi::class)
class FileViewModel(
    private val gistRepository: GistRepository,
) : ViewModel() {

    private var _gist = MediatorLiveData<GistAndAllFiles>()
    val gist : LiveData<GistAndAllFiles> = _gist

    private val _code = MutableLiveData<CodeData>()
    val code : LiveData<CodeData> = _code

    fun getGistById(gistId: String) {
        fetchGistUpdate(gistId)
        viewModelScope.launch(Dispatchers.IO) {
            _gist.addSource(gistRepository.getGistById(gistId)){
                _gist.value = it
            }
        }
    }

    private fun fetchGistUpdate(gistId: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("ABACATE", "File VM ${gistId}")
        gistRepository.updateGistDetails(gistId)
    }

    fun getCode(file : File){
        viewModelScope.launch(Dispatchers.IO) {
            val codeData = CodeData(url = file.raw_url, content = file.content, language = file.language)
            Log.d("ABACATE", "Code data : ${codeData.content?.isEmpty()}, ${codeData.language}")
            _code.postValue(codeData)
        }
    }

}


