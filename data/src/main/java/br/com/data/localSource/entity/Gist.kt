package br.com.data.localSource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Gist(
    @PrimaryKey() val id : String,
    val avatar_url: String,
    val owner_id: Int,
    val login: String,
    val fileCount : Int,
    val firstFileName : String?,
    val firstFileType : String?,
    val description: String,
    val page : Int,
    val favorite : Boolean = false
)


interface GistModel{
    fun toDbModel(page : Int) : Gist
}