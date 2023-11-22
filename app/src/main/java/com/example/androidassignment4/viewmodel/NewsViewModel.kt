package com.example.androidassignment4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidassignment4.model.Article
import com.example.androidassignment4.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<Article>>()
    val articleList: LiveData<List<Article>> get() = _newsList

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.fetchNews()
            _newsList.postValue(news)
        }
    }
}
