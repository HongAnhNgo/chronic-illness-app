package com.example.chronicillnessapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteArticle")
data class FavoriteArticleEntity(
    @PrimaryKey val id: String, // Unique ID for the article
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean = false // To track whether the article is favorited
)