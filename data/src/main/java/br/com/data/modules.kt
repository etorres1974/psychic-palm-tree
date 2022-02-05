package br.com.data


import androidx.room.Room
import br.com.data.apiSource.network.HttpClient
import br.com.data.localSource.GistDatabase
import br.com.data.repository.GistRepository
import org.koin.dsl.module
import org.koin.ext.getFullName

fun gistDatabase() = module {
    single {
        Room.databaseBuilder(
            get(),
            GistDatabase::class.java,
            GistDatabase::class.getFullName()
        ).build()
    }
}

fun dataBaseModules() = module{
    single{ get<GistDatabase>().gistDao() }
    single{ get<GistDatabase>().fileDao() }
}

fun servicesModules(baseUrl : String) = module{
    single { HttpClient(baseUrl) }
    single { get<HttpClient>().gitHubGistService() }
}

fun repositories() = module {
    single { GistRepository(get(), get())}
}


