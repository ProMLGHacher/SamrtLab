package com.example.samrtlab.feature.comfirm_email.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.samrtlab.R
import com.example.samrtlab.feature.comfirm_email.view_model.ConfirmEmailViewModel
import com.example.samrtlab.feature.navigation.model.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@Composable
fun ConfirmEmail(
    navController: NavController,
    viewModel: ConfirmEmailViewModel = hiltViewModel()
) {
    val text1 = remember {
        mutableStateOf("")
    }
    val text2 = remember {
        mutableStateOf("")
    }
    val text3 = remember {
        mutableStateOf("")
    }
    val text4 = remember {
        mutableStateOf("")
    }
    val timer = remember {
        mutableStateOf(60)
    }
    LaunchedEffect(key1 = text1.value + text2.value + text3.value + text4.value) {
        val text = text1.value + text2.value + text3.value + text4.value
        if (text.length == 4) {
            viewModel.submit(text)
        }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                is ConfirmEmailViewModel.UiEvent.Error -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
                is ConfirmEmailViewModel.UiEvent.Success -> {
                    navController.navigate(Screen.CreatePassword.route)
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        while (true) {
            if (timer.value > 0) {
                delay(1000)
                timer.value -= 1
            } else {
                delay(500)
            }
        }
    }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar(navController)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Введите код из E-mail", fontWeight = FontWeight.W600, fontSize = 17.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF5F5F9))
                        .border(1.dp, Color(0xFFEBEBEB)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = text1.value,
                        onValueChange = {
                            if (it.length == 1) {
                                text1.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            } else if (it.isEmpty()) {
                                text1.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Left)
                            } else {
                                text2.value = it.last().toString()
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF5F5F9))
                        .border(1.dp, Color(0xFFEBEBEB)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = text2.value,
                        onValueChange = {
                            if (it.length == 1) {
                                text2.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            } else if (it.isEmpty()) {
                                text2.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Left)
                            } else {
                                text3.value = it.last().toString()
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF5F5F9))
                        .border(1.dp, Color(0xFFEBEBEB)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = text3.value,
                        onValueChange = {
                            if (it.length == 1) {
                                text3.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            } else if (it.isEmpty()) {
                                text3.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Left)
                            } else {
                                text4.value = it.last().toString()
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF5F5F9))
                        .border(1.dp, Color(0xFFEBEBEB)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = text4.value,
                        onValueChange = {
                            if (it.length == 1) {
                                text4.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Right)
                            } else if (it.isEmpty()) {
                                text4.value = it
                                focusManager.moveFocus(focusDirection = FocusDirection.Left)
                            }
                        },
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }
            Text(
                "Отправить код повторно можно\nбудет через ${timer.value} секунд(ы)",
                textAlign = TextAlign.Center,
                color = Color(0xFF939396)
            )
        }
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
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = null,
                tint = Color(0xFF7E7E9A)
            )
        }
    }
}