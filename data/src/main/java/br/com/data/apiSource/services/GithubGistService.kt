package br.com.data.apiSource.services

import br.com.data.apiSource.models.GistDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubGistService : Api {

    @GET("/gists")
    suspend fun getGists(
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ) : Response<List<GistDTO>>

}