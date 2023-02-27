package com.example.samrtlab.feature.comfirm_email.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.model.RequestError
import com.example.samrtlab.domain.model.TokenData
import com.example.samrtlab.domain.repository.TokenRepository
import com.example.samrtlab.domain.repository.UserRepos
import com.example.samrtlab.feature.change_mail.view_model.ChangeMailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmEmailViewModel @Inject constructor(
    private val userRepos: UserRepos,
    private val httpClient: HttpClient,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    val uiEvent = MutableSharedFlow<UiEvent>()

    sealed class UiEvent {
        data class Success(
            val token: String
        ) : UiEvent()
        data class Error(
            val error: String
        ) : UiEvent()
    }

    fun submit(code: String) {
        viewModelScope.launch {
            try {
                val res = httpClient.post("/api/signin") {
                    headers {
                        append("email", userRepos.getUser().mail!!)
                        append("code", code)
                    }
                }
                when (res.status.value) {
                    200 -> {
                        tokenRepository.setToken(TokenData(res.body<TokenData>().token))
                        uiEvent.emit(UiEvent.Success(res.body<TokenData>().token!!))
                    }
                    422 -> {
                        uiEvent.emit(UiEvent.Error(res.body<RequestError>().errors))
                    }
                    else -> {
                        uiEvent.emit(UiEvent.Error("проблемка ${res.status.value}"))
                    }
                }

            } catch (e: Exception) {
                Log.e("aaa", e.message.toString())
                uiEvent.emit(UiEvent.Error("Похоже какието проблемы с сетью :("))
            }
        }
    }
}

