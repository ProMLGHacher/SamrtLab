package com.example.samrtlab.feature.cart.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samrtlab.domain.model.CartItem
import com.example.samrtlab.domain.model.catalog.CatalogItem
import com.example.samrtlab.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    data class CartState(
        val isLoading: Boolean = true,
        val cart: List<CartItem> = emptyList(),
        val sum: Int = 0
    )

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    init {
        updateCart()
    }

    fun add(item: CatalogItem) {
        cartRepository.addCart(
            CartItem(
                name = item.name,
                price = item.price,
                id = item.id
            )
        )
        updateCart()
    }

    fun removeCartItem(item: CartItem) {
        cartRepository.deleteItem(item.name)
        updateCart()
    }

    fun plus(item: CartItem) {
        cartRepository.addCountToCartItem(item.name)
        updateCart()
    }

    fun minus(item: CartItem) {
        cartRepository.decreaseCountFromCartItem(item.name)
        updateCart()
    }

    fun clear() {
        cartRepository.clear()
        updateCart()
    }

    fun updateCart() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            _state.update {
                it.copy(
                    cart = cartRepository.getCart()
                )
            }
            _state.update {
                var sum = 0
                it.cart.map {item ->
                    sum += item.price.toInt() * item.count
                }
                it.copy(
                    isLoading = false,
                    sum = sum
                )
            }
            _state.update {
                it.copy(
                    cart = it.cart.sortedBy { item ->
                        item.name
                    }
                )
            }
        }
    }

}