package br.com.data.localSource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.data.apiSource.models.GistDTO

@Entity
data class Gist(
    @PrimaryKey() val id : String
)

fun GistDTO.toDbModel() = Gist(
    id = id
)