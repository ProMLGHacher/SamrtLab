package com.example.samrtlab.feature.change_mail.view_model

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.model.RequestError
import com.example.samrtlab.domain.repository.UserRepos
import com.example.samrtlab.feature.change_mail.model.ChangeMailState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeMailViewModel @Inject constructor(
    private val httpClient: HttpClient,
    private val userRepos: UserRepos
) : ViewModel() {

    private val _state = MutableStateFlow(ChangeMailState())
    val state = _state.asStateFlow()

    val uiEvent = MutableSharedFlow<UiEvent>()

    fun changeEmail(value: String) {
       _state.update {
           it.copy(
               mail = value
           )
       }
        if (!Patterns.EMAIL_ADDRESS.matcher(_state.value.mail).matches()) {
            _state.update {
                it.copy(isCorrect = false)
            }
        } else {
            _state.update {
                it.copy(isCorrect = true)
            }
        }
    }

    fun submit() {
        viewModelScope.launch {
            try {
                val res = httpClient.post("/api/sendCode") {
                    headers {
                        append("email", _state.value.mail)
                    }
                }
                when(res.status.value) {
                    200 -> {
                        userRepos.setMail(_state.value.mail)
                        uiEvent.emit(UiEvent.Success)
                    }
                    422 -> {
                        uiEvent.emit(UiEvent.Error(res.body<RequestError>().errors))
                    }
                    else -> {
                        uiEvent.emit(UiEvent.Error("проблемка ${res.status.value}"))
                    }
                }

            }

            catch (e: Exception) {
                Log.e("aaa", e.message.toString())
                uiEvent.emit(UiEvent.Error("Похоже какието проблемы с сетью :("))
            }
        }
    }

    sealed class UiEvent {
        data class Error(
            val message: String
        ) : UiEvent()
        object Success : UiEvent()
    }

}