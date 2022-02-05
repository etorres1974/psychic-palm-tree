package br.com.data.localSource.entity

import androidx.room.Entity

@Entity(primaryKeys = ["owner_id", "filename"])
data class File(
    val owner_id: String,
    val filename: String,
    val language: String,
    val raw_url: String,
    val size: Int,
    val type: String
)

interface FileModel{
    fun getFilesDb() : List<File>
}