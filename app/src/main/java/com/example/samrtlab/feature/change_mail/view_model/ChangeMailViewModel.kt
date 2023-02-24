package com.example.samrtlab.feature.change_mail.view_model

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.samrtlab.feature.change_mail.model.ChangeMailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChangeMailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ChangeMailState())
    val state = _state.asStateFlow()

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

    sealed class UiEvent {
        object Correct : UiEvent()
    }

}