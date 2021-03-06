package br.com.freedomgist

import androidx.paging.ExperimentalPagingApi
import br.com.data.DataApplication
import org.koin.core.module.Module

@OptIn(ExperimentalPagingApi::class)
class App : DataApplication() {

    override fun allModules(): List<Module> =
        super.allModules().plus(
            listOf(
                flavourModules,
                viewModelsModules
            )
        )
}