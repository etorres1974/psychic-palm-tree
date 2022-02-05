package br.com.data.localSource.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.data.localSource.dao.BaseDao
import br.com.data.localSource.entity.Gist

@Dao
interface GistDao : BaseDao<Gist> {

    @Query("SELECT * FROM Gist")
    fun getAll(): List<Gist>
}