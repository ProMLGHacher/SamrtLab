package com.example.samrtlab.feature.cart.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.samrtlab.R
import com.example.samrtlab.consts.CustomButton
import com.example.samrtlab.domain.model.catalog.CatalogItem
import com.example.samrtlab.feature.cart.view_model.CartViewModel
import java.util.*

@Composable
fun Cart(
    appNavController: NavController, viewModel: CartViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.updateCart()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppBar(appNavController)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Корзина", fontWeight = FontWeight.W700, color = Color.Black, fontSize = 24.sp)
            IconButton(onClick = {
                viewModel.clear()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.trash),
                    contentDescription = null,
                    tint = Color(0xFFB8C1CC)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.value.cart) { item ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 20.dp)
                        .fillMaxWidth()
                        .border(2.dp, color = Color(0xFFF4F4F4), shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            item.name,
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color(0xFF7E7E9A),
                            modifier = Modifier.clickable {
                                viewModel.removeCartItem(item)
                            })
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            item.price + " ₽",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.Black
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                item.count.toString() + " " + scl(item.count),
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                                    .background(
                                        color = Color(0xFFF5F5F9), shape = RoundedCornerShape(
                                            topStart = 8.dp, bottomStart = 8.dp
                                        )
                                    )
                                    .clickable {
                                        viewModel.minus(item)
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.minus),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(0.7f),
                                    tint = if (item.count > 1) Color(0xFF939396) else Color(
                                        0xFFB8C1CC
                                    )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .height(32.dp)
                                    .width(1.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.5f)
                                        .background(Color(0xFFEBEBEB).copy(0.1f))
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                                    .background(
                                        color = Color(0xFFF5F5F9), shape = RoundedCornerShape(
                                            topEnd = 8.dp, bottomEnd = 8.dp
                                        )
                                    )
                                    .clickable {
                                        viewModel.plus(item)
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.plus),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(0.7f),
                                    tint = if (item.count < 9) Color(0xFF939396) else Color(
                                        0xFFB8C1CC
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(state.value.sum.toString() + " ₽", color = Color.Black, fontWeight = FontWeight.W600, fontSize = 20.sp)
            Text("Сумма", color = Color.Black, fontWeight = FontWeight.W600, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(text = "Перейти к оформлению заказа") {

        }
        Spacer(modifier = Modifier.height(32.dp))

    }

}

@Composable
private fun AppBar(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF5F5F9))
                .clickable {
                    navController.popBackStack()
                }, contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = null,
                tint = Color(0xFF7E7E9A)
            )
        }
    }
}

fun scl(count: Int): String {
    return when (count) {
        0 -> "Пациентов"
        1 -> "Пациент"
        2 -> "Пациента"
        3 -> "Пациента"
        4 -> "Пациента"
        5 -> "Пациентов"
        6 -> "Пациентов"
        7 -> "Пациентов"
        8 -> "Пациентов"
        9 -> "Пациентов"
        else -> ""
    }
}