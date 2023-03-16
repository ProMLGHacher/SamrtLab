package com.example.samrtlab.feature.order.view_model

import androidx.lifecycle.ViewModel
import com.example.samrtlab.feature.order.model.OrderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(OrderState())
    val state = _state.asStateFlow()

    fun setAddress(address: String) {
        _state.update {
            it.copy(
                address = address
            )
        }
    }

}