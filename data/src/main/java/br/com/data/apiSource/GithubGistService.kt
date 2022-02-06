package br.com.data.apiSource

import br.com.data.apiSource.models.GistDTO
import retrofit2.http.*
import retrofit2.Response


interface GithubGistService {

    @GET("/gists")
    suspend fun getGists(
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ) : Response<List<GistDTO>>

}