package com.example.samrtlab.feature.launch.view_model

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.repository.TokenRepository
import com.example.samrtlab.feature.launch.model.LaunchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchScreenViewModel @Inject constructor(
    sharedPreferences: SharedPreferences,
    tokenRepository: TokenRepository
) : ViewModel() {

    val _state = mutableStateOf<LaunchScreenState>(LaunchScreenState.Loading)
    val state by ::_state

    init {
        viewModelScope.launch {
            delay(500L)
            if (sharedPreferences.getBoolean("isFirstSession", true)) {
                sharedPreferences.edit().putBoolean("fs", true).apply()
                _state.value = LaunchScreenState.First
                return@launch
            }  else {
                sharedPreferences.edit().putBoolean("fs", false).apply()
            }
            if (tokenRepository.getToken().token == null) {
                _state.value = LaunchScreenState.Rejected
                return@launch
            }
            _state.value = LaunchScreenState.Success
        }
    }
}