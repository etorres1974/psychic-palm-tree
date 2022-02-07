package br.com.data.localSource.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.data.localSource.entity.Gist
import br.com.data.localSource.entity.GistFilter

@Dao
interface GistDao : BaseDao<Gist> {

    @Query("SELECT * FROM Gist")
    fun getAll(): List<Gist>

    @Query("SELECT * FROM Gist where favorite is 1")
    fun getFavorites(): List<Gist>

    @Query( "UPDATE Gist set favorite = :favorite where id = :gistId")
    fun favorite(gistId: String, favorite: Boolean): Int

    @Query("SELECT * FROM gist order by dbId")
    fun pagingSource(): PagingSource<Int, Gist>

    @Query("SELECT * FROM gist where gist.favorite == 1 order by dbId ")
    fun pagingSourceFavorites(): PagingSource<Int, Gist>
}
