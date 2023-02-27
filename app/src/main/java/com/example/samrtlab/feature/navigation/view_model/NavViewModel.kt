package com.example.samrtlab.feature.navigation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.model.TokenData
import com.example.samrtlab.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    httpClient: HttpClient,
    tokenRepository: TokenRepository
) : ViewModel() {

    val uiEvent = MutableSharedFlow<UiEvent>()

    sealed class UiEvent {
        object NoAuth : UiEvent()
    }

    init {
        viewModelScope.launch {
            httpClient.plugin(HttpSend).intercept { request ->
                val originalCall = execute(request)
                if (originalCall.response.status.value in listOf(401, 403)) {
                    tokenRepository.setToken(TokenData(null))
                    uiEvent.emit(UiEvent.NoAuth)
                }
                originalCall
            }
        }
    }

}