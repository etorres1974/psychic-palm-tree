package br.com.data.apiSource.services

import br.com.data.apiSource.models.GistDTO
import br.com.data.repository.GistRemoteMediator.Companion.GITHUB_STARTING_PAGE_INDEX
import br.com.data.repository.GistRemoteMediator.Companion.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubGistService : Api {

    @GET("/gists/public")
    suspend fun getGists(
        @Query("per_page") perPage: Int = PAGE_SIZE,
        @Query("page") page: Int = GITHUB_STARTING_PAGE_INDEX
    ) : Response<List<GistDTO>>

}