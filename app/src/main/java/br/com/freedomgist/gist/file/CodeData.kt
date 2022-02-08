package br.com.freedomgist.gist.file

import java.io.Serializable

data class CodeData(
    val url : String,
    val content : String?,
    val language : String
) : Serializable