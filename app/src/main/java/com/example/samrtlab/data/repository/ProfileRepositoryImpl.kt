package com.example.samrtlab.data.repository

import android.content.SharedPreferences
import android.net.Uri
import com.example.samrtlab.domain.model.Profile
import com.example.samrtlab.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : ProfileRepository {
    override fun setImage(uri: Uri?) {
        sharedPreferences.edit().putString(AVATAR, uri.toString()).apply()
    }

    override fun getImage(): Uri? {
        return try {
            Uri.parse(sharedPreferences.getString(AVATAR, null))
        } catch (e: Exception) {
            null
        }
    }

    override fun setProfile(profile: Profile) {
        profile.name?.let {
            sharedPreferences.edit().putString(NAME, it).apply()
        }
        profile.date?.let {
            sharedPreferences.edit().putString(DATE, it).apply()
        }
        profile.firstName?.let {
            sharedPreferences.edit().putString(FIRST_NAME, it).apply()
        }
        profile.lastName?.let {
            sharedPreferences.edit().putString(LAST_NAME, it).apply()
        }
        profile.gender?.let {
            sharedPreferences.edit().putString(GENDER, it).apply()
        }
    }

    override fun getProfile(): Profile {
        return Profile(
            name = sharedPreferences.getString(NAME, null),
            firstName = sharedPreferences.getString(FIRST_NAME, null),
            lastName = sharedPreferences.getString(LAST_NAME, null),
            date = sharedPreferences.getString(DATE, null),
            gender = sharedPreferences.getString(GENDER, null)
        )
    }

    companion object {
        const val NAME = "NAME"
        const val FIRST_NAME = "FIRST_NAME"
        const val LAST_NAME = "LAST_NAME"
        const val DATE = "DATE"
        const val GENDER = "GENDER"
        const val AVATAR = "AVATAR"
    }
}