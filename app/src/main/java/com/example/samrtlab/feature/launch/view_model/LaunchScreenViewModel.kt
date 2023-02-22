package com.example.samrtlab.feature.launch.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.feature.launch.model.LaunchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchScreenViewModel @Inject constructor() : ViewModel() {

    val _state = mutableStateOf<LaunchScreenState>(LaunchScreenState.Loading)
    val state by ::_state

    init {
        viewModelScope.launch {
            delay(500L)
            _state.value = LaunchScreenState.First
        }
    }

}