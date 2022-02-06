package br.com.freedomgist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.data.repository.GistRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val gistRepository: GistRepository
) : ViewModel() {

    init {

    }

    fun queryGists() =  viewModelScope.launch {
        gistRepository.queryGistAndSave()
    }
}