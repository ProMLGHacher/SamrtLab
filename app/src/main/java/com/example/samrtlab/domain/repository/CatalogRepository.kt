package com.example.samrtlab.domain.repository

import com.example.samrtlab.domain.model.catalog.CatalogItem

interface CatalogRepository {
    suspend fun getCatalog(
        category: String? = null
    ): List<CatalogItem>

    suspend fun getCategories(): List<String>
}