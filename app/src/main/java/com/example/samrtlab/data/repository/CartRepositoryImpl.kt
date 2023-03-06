package com.example.samrtlab.data.repository

import com.example.samrtlab.domain.model.CartItem
import com.example.samrtlab.domain.repository.CartRepository

class CartRepositoryImpl : CartRepository {

    val cart: MutableSet<CartItem> = mutableSetOf()

    override fun addCart(item: CartItem) {
        if (cart.any { cartItem -> cartItem.name == item.name }) return
        cart.add(item)
    }

    override fun getCart(): List<CartItem> {
        return cart.toList()
    }

    override fun deleteItem(name: String) {
        cart.removeIf {
            it.name == name
        }
    }
}