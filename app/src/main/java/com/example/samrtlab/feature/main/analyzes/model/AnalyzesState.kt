package com.example.samrtlab.feature.main.analyzes.model

import com.example.samrtlab.domain.model.news.NewsItem

data class AnalyzesState(
    val news: List<NewsItem> = emptyList(),
    val isLoading: Boolean = true
)
