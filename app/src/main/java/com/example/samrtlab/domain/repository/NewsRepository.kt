package com.example.samrtlab.domain.repository

import com.example.samrtlab.domain.model.news.NewsItem

interface NewsRepository {
    suspend fun getNews() : List<NewsItem>
}