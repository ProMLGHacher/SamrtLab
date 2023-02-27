package com.example.samrtlab.domain.repository

import com.example.samrtlab.domain.model.TokenData
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getToken() : TokenData
    fun setToken(tokenData: TokenData)
}