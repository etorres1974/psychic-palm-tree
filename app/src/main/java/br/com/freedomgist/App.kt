package br.com.freedomgist

import br.com.data.DataApplication
import org.koin.core.module.Module

class App : DataApplication() {

    override fun allModules(): List<Module> =
        super.allModules().plus(
            viewModelsModules
        )
}