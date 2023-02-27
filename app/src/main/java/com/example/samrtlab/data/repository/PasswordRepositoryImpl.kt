package com.example.samrtlab.data.repository

import android.content.SharedPreferences
import com.example.samrtlab.domain.repository.PasswordRepository

class PasswordRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : PasswordRepository {
    override fun getPassword(): String? {
        return  sharedPreferences.getString(PASSWORD_TAG, null)
    }

    override fun setPassword(password: String) {
        sharedPreferences.edit().putString(PASSWORD_TAG, password).apply()
    }

    companion object {
        const val PASSWORD_TAG = "password"
    }

}