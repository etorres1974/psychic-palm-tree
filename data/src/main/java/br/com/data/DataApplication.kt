package br.com.data

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin

open class DataApplication : Application() {
    lateinit var koinApp : KoinApplication

    override fun onCreate() {
        super.onCreate()
        koinApp = startKoin()
    }

    open fun startKoin() = startKoin{
        androidContext(this@DataApplication)
        modules(
            allModules()
        )
    }

    open fun allModules() = listOf(
        servicesModules(getBaseUrl()),
        roomInstance(),
        dataBaseModules(),
        repositories()
    )

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    open fun getBaseUrl() = GITHUB_GIST

    open fun roomInstance() = gistDatabase()

    companion object{
        const val GITHUB_GIST = "https://api.github.com"
    }
}

