package br.com.freedomgist

import androidx.paging.ExperimentalPagingApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalPagingApi::class)
val viewModelsModules = module {
    viewModel { MainViewModel(get(), get()) }
}

