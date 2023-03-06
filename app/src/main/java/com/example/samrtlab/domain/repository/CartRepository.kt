package com.example.samrtlab.domain.repository

import com.example.samrtlab.domain.model.CartItem

interface CartRepository {
    fun addCart(item: CartItem)
    fun getCart(): List<CartItem>
    fun deleteItem(name: String)
}