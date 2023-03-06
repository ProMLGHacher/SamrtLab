package com.example.samrtlab.feature.cart.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.samrtlab.domain.model.CartItem
import com.example.samrtlab.domain.model.catalog.CatalogItem
import com.example.samrtlab.feature.cart.view_model.CartViewModel
import java.util.Random

@Composable
fun Cart(
    appNavController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(state.value.cart.toString())

        Button(onClick = {
            viewModel.add(CatalogItem(
                bio = Random().nextInt().toString(),
                category = Random().nextInt().toString(),
                description = Random().nextInt().toString(),
                id = Random().nextInt(),
                name = Random().nextInt().toString(),
                preparation = Random().nextInt().toString(),
                price = Random().nextInt().toString(),
                time_result = Random().nextInt().toString()
            ))
        }) {
            Text("add")
        }
        Button(onClick = {
            viewModel.removeCartItem(
                CatalogItem(
                    bio = Random().nextInt().toString(),
                    category = Random().nextInt().toString(),
                    description = Random().nextInt().toString(),
                    id = Random().nextInt(),
                    name = state.value.cart[Random().nextInt(state.value.cart.size)].name,
                    preparation = Random().nextInt().toString(),
                    price = Random().nextInt().toString(),
                    time_result = Random().nextInt().toString()
                )
            )
        }) {
            Text("add")
        }

    }

}