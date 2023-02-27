package com.example.samrtlab.data.repository

import android.content.SharedPreferences
import com.example.samrtlab.domain.model.TokenData
import com.example.samrtlab.domain.repository.TokenRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer

class TokenRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : TokenRepository {

    override fun setToken(tokenData: TokenData) {
        sharedPreferences.edit().putString(TOKEN_TAG, tokenData.token).apply()
    }

    override fun getToken(): TokenData {
        return TokenData(
            token = sharedPreferences.getString(TOKEN_TAG, null)
        )
    }

    companion object {
        const val TOKEN_TAG = "token"
    }

}