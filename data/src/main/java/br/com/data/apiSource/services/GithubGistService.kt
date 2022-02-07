package br.com.data.apiSource.services

import br.com.data.apiSource.models.GistDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubGistService : Api {

    @GET("/gists/public")
    suspend fun getGists(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ) : Response<List<GistDTO>>

}