package br.com.data.apiSource

import br.com.data.apiSource.models.GistDTO
import retrofit2.http.*
import retrofit2.Response


interface GithubGistService {

    @GET("/gists")
    suspend fun getGists() : Response<List<GistDTO>>
}