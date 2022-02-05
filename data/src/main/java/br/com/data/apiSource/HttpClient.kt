package br.com.data.apiSource

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient(
    private val baseUrl : String
) {

    private val loggerInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okhttpClient = OkHttpClient.Builder().addInterceptor(loggerInterceptor).build()

    private fun retrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(JsonHelper().gson))
        .client(okhttpClient)
        .baseUrl(baseUrl)
        .build()

    fun gitHubGistService(): GithubGistService = retrofit().create(GithubGistService::class.java)
}