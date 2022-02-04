package br.com.data

import br.com.data.models.GistDTO
import retrofit2.http.*
import retrofit2.Response


interface GithubGistService {

    @GET("/gists")
    suspend fun getGists() : Response<List<GistDTO>>
}