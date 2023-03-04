package com.example.samrtlab.feature.main.analyzes.model

import com.example.samrtlab.domain.model.catalog.CatalogItem

data class CatalogState(
    val catalog: List<CatalogItem> = emptyList(),
    val categories: List<String> = emptyList(),
    val catalogIsLoading: Boolean = true,
    val categoriesIsLoading: Boolean = true,
    val selectedCategory: String? = null,
    val searchText: String = "",
)
