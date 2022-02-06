package br.com.data.localSource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Auth(
    @PrimaryKey val token : String,
    val type : String
){
    override fun toString(): String = "$type $token"
}
