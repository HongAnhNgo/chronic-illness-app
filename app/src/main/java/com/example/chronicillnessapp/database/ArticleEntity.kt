package com.example.chronicillnessapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
    var isFavorite: Boolean = false // To track whether the article is favorited
)
