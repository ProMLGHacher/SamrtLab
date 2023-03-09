package com.example.samrtlab.data.repository

import com.example.samrtlab.domain.model.CartItem
import com.example.samrtlab.domain.repository.CartRepository

class CartRepositoryImpl : CartRepository {

    val cart: MutableSet<CartItem> = mutableSetOf()

    override fun addCart(item: CartItem) {
        if (cart.any { cartItem -> cartItem.name == item.name }) return
        cart.add(item)
    }

    override fun addCountToCartItem(name: String) {
        cart.onEachIndexed { index, it ->
            if (it.name == name) {
                if (it.count >= 9) return
                val ele = it.copy(count = it.count+1)
                cart.remove(it)
                cart.add(ele)
                return
            }
        }
    }

    override fun clear() {
        cart.clear()
    }

    override fun decreaseCountFromCartItem(name: String) {
        cart.onEachIndexed { index, cartItem ->
           if (cartItem.name == name) {
               if (cartItem.count <= 1) {
//                deleteItem(cartItem.name)
                   return
               } else {
                   cart.remove(cartItem)
                   cart.add(cartItem.copy(count = cartItem.count - 1))
                   return
               }
           }
        }
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