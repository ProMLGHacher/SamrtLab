package com.example.samrtlab.feature.main.profile.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.model.Profile
import com.example.samrtlab.domain.repository.ProfileRepository
import com.example.samrtlab.feature.main.profile.model.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val user = profileRepository.getProfile()
            _state.update {
                it.copy(
                    imageUri = profileRepository.getImage(),
                    name = user.name ?: "",
                    firstName = user.firstName ?: "",
                    lastName = user.lastName ?: "",
                    gender = user.gender ?: "Пол",
                    date = user.date ?: ""
                )
            }
        }
    }

    fun submit() {
        profileRepository.setProfile(
            Profile(
                name = _state.value.name,
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                date = _state.value.date,
                gender = _state.value.gender
            )
        )
    }

    fun setName(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    name = value
                )
            }
        }
    }

    fun setFirstName(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    firstName = value
                )
            }
        }
    }

    fun setLastName(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    lastName = value
                )
            }
        }
    }

    fun setDate(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    date = value
                )
            }
        }
    }

    fun setGender(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    gender = value
                )
            }
        }
    }

    fun setImageUri(uri: Uri?) {
        profileRepository.setImage(uri)
        viewModelScope.launch {
            _state.update {
                it.copy(
                    imageUri = profileRepository.getImage()
                )
            }
        }
    }

}