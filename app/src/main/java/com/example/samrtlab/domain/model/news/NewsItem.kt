package com.example.samrtlab.domain.model.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    val description: String,
    val id: Int,
    val image: String,
    val name: String,
    val price: String
)