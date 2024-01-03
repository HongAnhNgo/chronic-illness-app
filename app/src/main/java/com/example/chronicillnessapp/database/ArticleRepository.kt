package com.example.chronicillnessapp.database

import com.example.chronicillnessapp.interfaces.ArticleInterface

class ArticleRepository(private val apiService: ArticleInterface) {
    suspend fun getChronicArticles(): List<ArticleEntity> {
        val response = apiService.getChronicArticles()

        // Adjusted according to the JSON structure
        return response.body()?.Result?.Resources?.Resource?.map { resource ->
            ArticleEntity(
                id = resource.Id, // Adjust based on your ArticleEntity structure
                title = resource.Title,
                imageUrl = resource.ImageUrl,
                isFavorite = false
            )
        } ?: emptyList()
    }
}



