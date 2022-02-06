package br.com.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.data.localSource.entity.Auth

@Dao
interface AuthDao : BaseDao<Auth>  {

    @Query("SELECT * FROM Auth LIMIT 1")
    fun getAll(): List<Auth>
}