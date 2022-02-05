package br.com

import android.content.Context
import androidx.room.Room
import br.com.data.DataApplication
import br.com.data.localSource.GistDatabase
import br.com.data.localSource.dao.FileDao
import br.com.data.localSource.dao.GistDao
import br.com.data.repository.GistRepository
import org.koin.android.ext.android.get
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class DataTestRunner : DataApplication(){

    lateinit var db : GistDatabase
    val fileDao : FileDao by lazy { get() }
    val gistDao : GistDao by lazy { get() }
    val gistRepository : GistRepository by lazy { get() }

    override fun startKoin(): KoinApplication {
        setup()
        return super.startKoin()
    }

    private fun setup(){
        db =  gistDatabase(this)
    }

    override fun getBaseUrl() = MOCK_BASE_URL

    override fun roomInstance() = module {
        single { db }
    }

    private fun gistDatabase(ctx : Context) = Room.inMemoryDatabaseBuilder(
        ctx,
        GistDatabase::class.java
    ).build()

    companion object{
        const val MOCK_BASE_URL = "http://127.0.0.1:8080"
    }
}
