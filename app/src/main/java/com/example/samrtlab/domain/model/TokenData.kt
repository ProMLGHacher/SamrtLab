package com.example.samrtlab.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenData(
    val token: String?
)