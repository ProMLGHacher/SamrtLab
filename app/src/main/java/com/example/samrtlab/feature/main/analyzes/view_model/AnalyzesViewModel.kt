package com.example.samrtlab.feature.main.analyzes.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.repository.NewsRepository
import com.example.samrtlab.feature.main.analyzes.model.AnalyzesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class AnalyzesViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AnalyzesState())
    val state = _state.asStateFlow()

    init {
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