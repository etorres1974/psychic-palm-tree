package br.com.data.localSource.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["id"], unique = true)])
data class Gist(
    @PrimaryKey (autoGenerate = true) val dbId : Int = 0,
    val id : String,
    val avatar_url: String,
    val owner_id: Int,
    val login: String,
    val fileCount : Int,
    val firstFileName : String?,
    val firstFileType : String?,
    val description: String,
    val page : Int,
    val updated_at: String,
    val created_at: String,
    val favorite : Boolean = false
    )


interface GistModel{
    fun toDbModel(page : Int) : Gist
}