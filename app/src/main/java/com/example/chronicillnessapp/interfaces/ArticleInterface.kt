package com.example.chronicillnessapp.interfaces

import com.example.chronicillnessapp.network.ApiResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ArticleInterface {
    @GET("topicsearch.json")
    suspend fun getChronicArticles(
        @Query("lang") lang: String = "en",
        @Query("keyword") keyword: String = "chronic"
    ): Response<ApiResponse> // Replace with your actual response class
}
