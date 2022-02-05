package br.com.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.data.localSource.entity.File

@Dao
interface FileDao : BaseDao<File> {

    @Query("SELECT * FROM File")
    fun getAll(): List<File>

}