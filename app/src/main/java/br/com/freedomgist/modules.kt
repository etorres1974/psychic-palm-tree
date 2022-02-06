package br.com.freedomgist

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModules = module {
    viewModel { MainViewModel(get(), get()) }
}

