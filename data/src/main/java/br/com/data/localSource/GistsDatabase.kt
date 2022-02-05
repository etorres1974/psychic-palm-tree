package br.com.data.localSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.data.localSource.dao.GistDao
import br.com.data.localSource.entity.Gist

@Database(entities = [Gist::class], version = 1, exportSchema = false)
abstract class GistDatabase : RoomDatabase() {
    abstract fun gistDao() : GistDao
}

fun getDatabase(appContext : Context) = Room.databaseBuilder(
    appContext,
    GistDatabase::class.java, GistDatabase::class.toString()
)