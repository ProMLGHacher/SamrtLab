package com.example.samrtlab.data.repository

import com.example.samrtlab.domain.model.news.NewsItem
import com.example.samrtlab.domain.repository.NewsRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsRepositoryImpl(
    private val httpClient: HttpClient
) : NewsRepository {

    override suspend fun getNews(): List<NewsItem> {
        return httpClient.get("/api/news").body()
    }

}