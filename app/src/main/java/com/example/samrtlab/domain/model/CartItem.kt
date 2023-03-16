package com.example.samrtlab.domain.model

data class CartItem(
    val name: String,
    val price: String,
    val count: Int = 1,
    val id: Int,
)
