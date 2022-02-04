package br.com.data

import br.com.data.models.GistDTO
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {

    private val baseUrl = "https://api.github.com"

    private val okhttpClient = OkHttpClient.Builder().build()

    private val gistFileDeserializer = JsonDeserializer { jsonElement, _, context ->
        val jsonObject = jsonElement.asJsonObject
        val gistFiles : List<GistDTO.File> =
            jsonObject.entrySet().map{ context.deserialize(it.value, GistDTO.File::class.java)  }
        GistDTO.Files(gistFiles)
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(GistDTO.Files::class.java, gistFileDeserializer)
        .create()

    private fun retrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okhttpClient)
        .baseUrl( baseUrl)
        .build()

    fun gitHubGistService(): GithubGistService = retrofit().create(GithubGistService::class.java)
}