package com.example.samrtlab.feature.create_password.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.data.repository.PasswordRepositoryImpl
import com.example.samrtlab.domain.repository.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository
) : ViewModel() {

    sealed class State {
        object Loading : State()
        object HasPassword : State()
        object NotHasPassword : State()
    }

    sealed class UiEvent {
        object Success : UiEvent()
        object Rejected : UiEvent()
    }

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    val uiEvent = MutableSharedFlow<UiEvent>()

    init {
        _state.update {
            if (passwordRepository.getPassword() == null) {
                State.NotHasPassword
            } else
            {
                State.HasPassword
            }
        }
    }

    fun execute(password: String) {
        viewModelScope.launch {
            if (_state.value is State.HasPassword) {
                if (password == passwordRepository.getPassword()) {
                    uiEvent.emit(UiEvent.Success)
                } else {
                    uiEvent.emit(UiEvent.Success)
                }
            }
            if (_state.value is State.NotHasPassword) {
                passwordRepository.setPassword(password = password)
                uiEvent.emit(UiEvent.Success)
            }
        }
    }

}