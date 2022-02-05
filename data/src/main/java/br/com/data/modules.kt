package br.com.data


import androidx.room.Room
import br.com.data.apiSource.HttpClient
import br.com.data.localSource.GistDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ext.getFullName

fun applicationModules(baseUrl: String) =
    listOf(
        httpClientModule(baseUrl),

    )


fun gistDatabase() = module {
    single {
        Room.databaseBuilder(
            get(),
            GistDatabase::class.java,
            GistDatabase::class.getFullName()
        ).build()
    }
}


fun httpClientModule(baseUrl : String) = module{
    single { HttpClient(baseUrl) }
}


