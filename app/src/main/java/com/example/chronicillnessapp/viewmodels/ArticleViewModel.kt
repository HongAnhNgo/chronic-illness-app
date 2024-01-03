package com.example.chronicillnessapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.chronicillnessapp.database.ArticleDao
import com.example.chronicillnessapp.database.ArticleEntity
import com.example.chronicillnessapp.interfaces.ArticleInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleDao: ArticleDao, private val apiService: ArticleInterface) : ViewModel() {

    // LiveData for all articles
    val allArticles = liveData(Dispatchers.IO) {
        emit(articleDao.getAllArticles())
    }

    // LiveData for favorite articles
    val favoriteArticles = liveData(Dispatchers.IO) {
        emit(articleDao.getFavoriteArticles())
    }

    // LiveData for chronic articles
    val chronicArticles = liveData(Dispatchers.IO) {
        try {
            val response = apiService.getChronicArticles()
            if (response.isSuccessful && response.body() != null) {
                val articles = response.body()?.Result?.Resources?.Resource?.map { apiResource ->
                    ArticleEntity(
                        id = apiResource.Id,
                        title = apiResource.Title,
                        imageUrl = apiResource.ImageUrl,
                        isFavorite = false
                    )
                } ?: emptyList()
                emit(articles)
            } else {
                Log.d("ArticleViewModel", "API Call Failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.d("ArticleViewModel", "Error fetching articles: ${e.message}")
        }
    }



    fun updateArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            articleDao.updateArticle(article)
        }
    }
}


class ArticleViewModelFactory(private val articleDao: ArticleDao, private val apiService: ArticleInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(articleDao, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
