package com.example.samrtlab.feature.main.profile.model

import android.net.Uri

data class ProfileState(
    val imageUri: Uri? = null,
    val name: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val date: String = "",
    val gender: String = ""
)
