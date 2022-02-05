package br.com

import br.com.data.apiSource.JsonHelper
import br.com.data.apiSource.models.GistDTO

object MockGistProvider {
    val gson = JsonHelper().gson

    fun getSingle() = gson.fromJson(fileReader("SingleGist.json"), GistDTO::class.java)

}