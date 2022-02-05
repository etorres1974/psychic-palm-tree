package br.com

import android.app.Application

open class DataApplication : Application() {

    open fun getBaseUrl() = GITHUB_GIST


    companion object{
        const val GITHUB_GIST = "https://api.github.com"
    }
}

