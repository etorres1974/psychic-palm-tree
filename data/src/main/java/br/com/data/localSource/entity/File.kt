package br.com.data.localSource.entity

import androidx.room.Entity

@Entity(primaryKeys = ["raw_url"])
data class File(
    val owner_id: Int,
    val filename: String,
    val language: String,
    val raw_url: String,
    val content: String?,
    val size: Int,
    val type: String
)

interface FileModel{
    fun getFilesDb() : List<File>
}