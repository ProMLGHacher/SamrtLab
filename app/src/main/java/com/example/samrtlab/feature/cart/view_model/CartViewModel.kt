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
        val cart: List<CartItem> = emptyList()
    )

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    fun add(item: CatalogItem) {
        cartRepository.addCart(
            CartItem(
                name = item.name,
                price = item.price
            )
        )
        updateCart()
    }

    fun removeCartItem(item: CatalogItem) {
        cartRepository.deleteItem(item.name)
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
                    isLoading = false,
                    cart = cartRepository.getCart()
                )
            }
        }
    }

}