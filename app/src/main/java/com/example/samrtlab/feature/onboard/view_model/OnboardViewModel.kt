package com.example.samrtlab.feature.onboard.view_model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun setOnboardChanged() {
        sharedPreferences.edit().putBoolean("isFirstSession", false).apply()
    }

}