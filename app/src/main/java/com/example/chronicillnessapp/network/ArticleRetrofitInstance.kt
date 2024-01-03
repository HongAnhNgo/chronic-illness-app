package com.example.chronicillnessapp.network

import com.example.chronicillnessapp.interfaces.ArticleInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ArticleRetrofitInstance {
    val apiService: ArticleInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://health.gov/myhealthfinder/api/v3/") // Replace with the correct base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleInterface::class.java)
    }
}