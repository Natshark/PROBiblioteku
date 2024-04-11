/*
package com.example.probiblioteku
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    fun fetchData(): Call<String> // Здесь String может быть заменен на модель данных
}

object RetrofitClient {
    private const val BASE_URL = "http://127.0.0.1:5000"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

*/
