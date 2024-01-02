package com.example.chronicillnessapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.chronicillnessapp.database.ArticleDao
import com.example.chronicillnessapp.database.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleDao: ArticleDao) : ViewModel() {

    // LiveData for all articles
    val allArticles = liveData(Dispatchers.IO) {
        emit(articleDao.getAllArticles())
    }

    // LiveData for favorite articles
    val favoriteArticles = liveData(Dispatchers.IO) {
        emit(articleDao.getFavoriteArticles())
    }

    fun updateArticle(article: ArticleEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            articleDao.updateArticle(article)
        }
    }
}


class ArticleViewModelFactory(private val articleDao: ArticleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(articleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}