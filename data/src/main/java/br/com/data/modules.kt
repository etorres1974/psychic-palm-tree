package br.com.data


import androidx.room.Room
import br.com.data.apiSource.services.Github
import br.com.data.apiSource.services.GithubGistService
import br.com.data.apiSource.network.HttpClient
import br.com.data.localSource.GistDatabase
import br.com.data.repository.AuthRepository
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
    single{ get<GistDatabase>().authDao() }
}

fun servicesModules(baseUrl : String, authUrl : String) = module{
    single { HttpClient<GithubGistService>(baseUrl).service() as GithubGistService }
    single { HttpClient<Github>(authUrl).service() as Github }
}

fun repositories() = module {
    single { GistRepository(get(), get())}
    single { AuthRepository(get(), get()) }
}



