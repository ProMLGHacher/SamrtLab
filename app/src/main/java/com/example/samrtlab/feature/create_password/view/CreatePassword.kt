package com.example.samrtlab.feature.create_password.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.samrtlab.R
import com.example.samrtlab.feature.create_password.view_model.PasswordViewModel
import com.example.samrtlab.feature.navigation.model.Screen
import kotlinx.coroutines.flow.collect

@Composable
fun CreatePassword(
    navController: NavController,
    viewModel: PasswordViewModel = hiltViewModel()
) {
    val password = remember {
        mutableStateOf("")
    }
    val state = viewModel.state.collectAsState()
    LaunchedEffect(key1 = password.value) {
        if (password.value.length >=5) {
            password.value = password.value.take(4)
        }
        if (password.value.length == 4) {
            viewModel.execute(password = password.value)
        }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                PasswordViewModel.UiEvent.Rejected -> {
                    Toast.makeText(context, "Пароль не верный", Toast.LENGTH_SHORT).show()
                    password.value = ""
                }
                is PasswordViewModel.UiEvent.Success -> {
                    if (state.value is PasswordViewModel.State.NotHasPassword) {
                        if (it.isFirstSession) {
                            navController.navigate(Screen.CreateMap.route){
                                popUpTo(Screen.CreatePassword.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.navigate(Screen.MainScreen.route) {
                                popUpTo(Screen.CreatePassword.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    if (state.value is PasswordViewModel.State.HasPassword) {
                        if (it.isFirstSession) {
                            navController.navigate(Screen.CreateMap.route){
                                popUpTo(Screen.CreatePassword.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(Screen.CreatePassword.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (state.value is PasswordViewModel.State.NotHasPassword) AppBar(navController, state.value)
        Spacer(modifier = Modifier.weight(1f))
        Text(if (state.value is PasswordViewModel.State.HasPassword) "Введите пароль" else "Создайте пароль", fontWeight = FontWeight.W700, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Для защиты ваших персональных данных",
            textAlign = TextAlign.Center,
            color = Color(0xFF939396),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Indicator(password.value)
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "1"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("1", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "2"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("2", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "3"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("3", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "4"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("4", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "5"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("5", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "6"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("6", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "7"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("7", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "8"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("8", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "9"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("9", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(color = Color(0xFFF5F5F9), shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value += "0"
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text("0", fontWeight = FontWeight.W600, fontSize = 24.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = Color(0xFF1A6FEE)),
                        ) {
                            password.value = password.value.dropLast(1)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.del_icon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.5f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun Indicator(
    password: String
) {
    Row {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(16.dp)
                .clip(CircleShape)
                .border(1.dp, color = Color(0xFF1A6FEE), shape = CircleShape)
                .background(
                    color = if (password.isNotEmpty()) Color(0xFF1A6FEE) else Color.Transparent,
                    shape = CircleShape
                ),
        )
        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(16.dp)
                .clip(CircleShape)
                .border(1.dp, color = Color(0xFF1A6FEE), shape = CircleShape)
                .background(
                    color = if (password.length > 1) Color(0xFF1A6FEE) else Color.Transparent,
                    shape = CircleShape
                ),
            )
        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(16.dp)
                .clip(CircleShape)
                .border(1.dp, color = Color(0xFF1A6FEE), shape = CircleShape)
                .background(
                    color = if (password.length > 2) Color(0xFF1A6FEE) else Color.Transparent,
                    shape = CircleShape
                ),
            )
        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(16.dp)
                .clip(CircleShape)
                .border(1.dp, color = Color(0xFF1A6FEE), shape = CircleShape)
                .background(
                    color = if (password.length > 3) Color(0xFF1A6FEE) else Color.Transparent,
                    shape = CircleShape
                ),
            )
    }
}

@Composable
private fun AppBar(
    navController: NavController,
    state: PasswordViewModel.State
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(12.dp), horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { navController.navigate(Screen.CreateMap.route) }
        ) {
            Text("Пропустить", color = if (state is PasswordViewModel.State.HasPassword) Color.Transparent else Color(0xFF1A6FEE), fontSize = 15.sp)
        }
    }
}