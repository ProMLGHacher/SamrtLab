package com.example.samrtlab.feature.main.analyzes.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.repository.NewsRepository
import com.example.samrtlab.feature.main.analyzes.model.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state = _state.asStateFlow()

    init {
        update()
    }

    fun update() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            runCatching {
                newsRepository.getNews()
            }.onSuccess { news ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        news = news
                    )
                }
            }.onFailure {
                Log.e("aaaaaaaa>>", it::class.simpleName.toString())
            }
        }
    }

}