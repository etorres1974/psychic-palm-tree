package br.com.freedomgist

import androidx.paging.ExperimentalPagingApi
import br.com.freedomgist.gist.GistViewModel
import br.com.freedomgist.gist.file.FileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val viewModelsModules = module {
    viewModel { GistViewModel(get(), get()) }
    viewModel { FileViewModel(get()) }
}

