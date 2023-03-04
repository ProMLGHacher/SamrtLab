package com.example.samrtlab.domain.repository

import android.net.Uri
import com.example.samrtlab.domain.model.Profile

interface ProfileRepository {
    fun setImage(uri: Uri?)
    fun getImage() : Uri?
    fun setProfile(profile: Profile)
    fun getProfile() : Profile
}