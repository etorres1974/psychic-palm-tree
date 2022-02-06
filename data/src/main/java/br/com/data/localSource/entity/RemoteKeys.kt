package br.com.data.localSource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val gistId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)