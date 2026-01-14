package com.example.recipeapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface RecipeApiService {

    @POST("/api/recommend")
    suspend fun getRecipes(@Body request: FoodListRequest): RecipeResponse

    companion object {
        private const val BASE_URL = "http://172.25.128.1:8000/"  // Use 10.0.2.2 for Android emulator to reach localhost

        fun create(): RecipeApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RecipeApiService::class.java)
        }
    }
}
