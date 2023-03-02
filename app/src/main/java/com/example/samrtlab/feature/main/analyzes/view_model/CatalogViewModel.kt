package com.example.samrtlab.feature.main.analyzes.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.repository.CatalogRepository
import com.example.samrtlab.feature.main.analyzes.model.CatalogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogState())
    val state = _state.asStateFlow()

    init {
        updateCategories()
    }

    fun setCategory(category: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCategory = category
                )
            }
        }
        updateCatalog()
    }

    private fun updateCatalog() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    catalogIsLoading = true
                )
            }
            _state.update {
                it.copy(
                    catalog = catalogRepository.getCatalog(it.selectedCategory),
                    catalogIsLoading = false
                )
            }
        }
    }

    fun updateCategories() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    categoriesIsLoading = true
                )
            }
            _state.update {
                it.copy(
                    categories = catalogRepository.getCategories(),
                    categoriesIsLoading = false
                )
            }
            _state.update {
                it.copy(
                    selectedCategory = it.categories.first()
                )
            }
            updateCatalog()
        }
    }

}