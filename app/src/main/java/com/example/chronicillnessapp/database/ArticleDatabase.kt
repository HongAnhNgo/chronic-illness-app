package com.example.chronicillnessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class, FavoriteArticleEntity::class], version = 2, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "articles_database"
                )
                    .fallbackToDestructiveMigration() // Enable destructive migration
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
