package br.com.freedomgist

import androidx.paging.ExperimentalPagingApi
import br.com.data.DataApplication
import io.github.kbiakov.codeview.classifier.CodeProcessor
import org.koin.core.module.Module

@OptIn(ExperimentalPagingApi::class)
class App : DataApplication() {

    override fun onCreate() {
        super.onCreate()
        CodeProcessor.init(this)
    }

    override fun allModules(): List<Module> =
        super.allModules().plus(
            viewModelsModules
        )
}