package br.com.data.localSource.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import br.com.data.localSource.entity.Gist

@Dao
interface GistDao : BaseDao<Gist> {

    @Query("SELECT * FROM Gist")
    fun getAll(): List<Gist>

    @Query("SELECT * FROM Gist where favorite is 1")
    fun getFavorites(): List<Gist>

    @Query( "UPDATE Gist set favorite = :favorite where id = :gistId")
    fun favorite(gistId: String, favorite: Boolean): Int

    @Query("SELECT * FROM gist ")
    fun pagingSource(): PagingSource<Int, Gist>

}