package com.example.chronicillnessapp.database

import androidx.room.*

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE isFavorite = 1")
    fun getFavoriteArticles(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleEntity)

    @Update
    fun updateArticle(article: ArticleEntity)

    @Delete
    fun deleteArticle(article: ArticleEntity)
}
