package com.example.samrtlab.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestError(
    val errors: String
)