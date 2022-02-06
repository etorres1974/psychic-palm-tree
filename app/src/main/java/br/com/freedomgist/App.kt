package br.com.freedomgist

import androidx.paging.ExperimentalPagingApi
import br.com.data.DataApplication
import org.koin.core.module.Module

@ExperimentalPagingApi
class App : DataApplication() {

    override fun allModules(): List<Module> =
        super.allModules().plus(
            viewModelsModules
        )
}