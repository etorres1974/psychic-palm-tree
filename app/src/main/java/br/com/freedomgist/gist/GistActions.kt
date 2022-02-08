package br.com.freedomgist.gist

interface GistActions {
    fun onClickGist(gistId : String) : Unit
    fun onClickOwner(ownerId : Int) : Unit
    fun onFavoriteGist(favorite : Boolean, gistId : String) : Unit
}