package br.com

import android.app.Application
import br.com.data.applicationModules
import br.com.data.gistDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

open class DataApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    open  fun startKoin() = startKoin{
        androidContext(this@DataApplication)
        module {
            applicationModules(getBaseUrl())
            roomModules()
        }
    }

    open fun getBaseUrl() = GITHUB_GIST

    open fun roomModules() = gistDatabase()

    companion object{
        const val GITHUB_GIST = "https://api.github.com"
    }
}

