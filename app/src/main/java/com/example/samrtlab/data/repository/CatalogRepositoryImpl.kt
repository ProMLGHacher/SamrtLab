package com.example.samrtlab.data.repository

import com.example.samrtlab.domain.model.catalog.CatalogItem
import com.example.samrtlab.domain.repository.CatalogRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CatalogRepositoryImpl(
    private val httpClient: HttpClient
) : CatalogRepository {
    override suspend fun getCatalog(category: String?): List<CatalogItem> {
        return httpClient.get("/api/catalog").body<List<CatalogItem>>().filter {
            category ?: return@filter true
            it.category == category
        }
    }

    override suspend fun getCategories(): List<String> {
        TODO("Not yet implemented")
    }
}