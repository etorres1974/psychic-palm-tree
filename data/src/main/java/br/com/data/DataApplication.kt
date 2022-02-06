package br.com.data

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin

open class DataApplication : MultiDexApplication() {
    lateinit var koinApp : KoinApplication

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

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
        servicesModules(getBaseUrl(), getAuthUrl()),
        roomInstance(),
        dataBaseModules(),
        repositories()
    )

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }

    open fun getBaseUrl() = GITHUB_GIST
    open fun getAuthUrl() = GITHUB

    open fun roomInstance() = gistDatabase()

    companion object{
        const val GITHUB_GIST = "https://api.github.com"
        const val GITHUB = "https://github.com"
    }
}

