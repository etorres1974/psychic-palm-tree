package br.com.data.localSource

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.data.localSource.dao.AuthDao
import br.com.data.localSource.dao.FileDao
import br.com.data.localSource.dao.GistDao
import br.com.data.localSource.dao.RemoteKeysDao
import br.com.data.localSource.entity.Auth
import br.com.data.localSource.entity.File
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.RemoteKeys

@Database(entities = [Gist::class, File::class, Auth::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class GistDatabase : RoomDatabase() {
    abstract fun gistDao() : GistDao
    abstract fun fileDao() : FileDao
    abstract fun authDao() : AuthDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}