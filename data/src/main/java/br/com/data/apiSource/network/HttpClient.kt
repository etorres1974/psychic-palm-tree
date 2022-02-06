package br.com.data.apiSource.network

import br.com.data.apiSource.network.utils.*
import br.com.data.apiSource.services.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  HttpClient<T : Api>(
    private val baseUrl : String
) {

    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(LOGGER_INTERCEPTOR)
        .addInterceptor(DEFAULT_HEADER_INTERCEPTOR)
        .build()

    fun retrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(JsonHelper().gson))
        .client(okhttpClient)
        .baseUrl(baseUrl)
        .build()

     inline fun <reified T> service(): T = retrofit().create(T::class.java)
}