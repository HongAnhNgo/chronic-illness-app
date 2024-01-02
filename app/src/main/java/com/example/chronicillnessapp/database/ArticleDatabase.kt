package com.example.chronicillnessapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ArticleEntity::class, FavoriteArticleEntity::class], version = 1, exportSchema = false)
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
                    .addCallback(ArticleDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ArticleDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.articleDao())
                }
            }
        }

        suspend fun populateDatabase(articleDao: ArticleDao) {
            // Populate the database with the initial data set
            articleDao.insertArticle( ArticleEntity(
                id = 1,
                title = "Revolutionizing Heart Health: Breakthroughs in Cardiology",
                imageUrl = "https://ts2.space/wp-content/uploads/2023/12/compressed_img-5uPVZECXlY0ThhswtH6dW5XX-1024x585.png"
            )
                )
            // Add more articles in the same way
            // ...
        }
    }
}
