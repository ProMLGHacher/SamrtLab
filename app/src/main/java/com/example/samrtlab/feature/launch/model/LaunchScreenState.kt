package com.example.samrtlab.feature.launch.model

sealed class LaunchScreenState {
    object Loading : LaunchScreenState()
    object Success : LaunchScreenState()
    object Rejected : LaunchScreenState()
    object First : LaunchScreenState()
}
