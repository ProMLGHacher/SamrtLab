package com.example.samrtlab.feature.create_map.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.samrtlab.core.elevation
import com.example.samrtlab.feature.create_map.view_model.CreateMapViewModel
import com.example.samrtlab.feature.navigation.model.Screen

@Composable
fun CreateMap(
    navController: NavController,
    viewModel: CreateMapViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(38.dp))
        AppBar(navController)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Без карты пациента вы не сможете заказать анализы.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = Color(0xFF939396)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = Color(0xFF939396)
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = state.value.name,
            onValueChange = {
                viewModel.setName(it)
            },
            placeholder = {
                Text("Имя", color = Color(0xFF939396), fontSize = 14.sp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            interactionSource = remember {
                MutableInteractionSource()
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF5F5F9),
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color(0xFFB8C1CC),
                unfocusedIndicatorColor = Color(0xFFEBEBEB),

                )
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = state.value.lastName,
            onValueChange = {
                viewModel.setLastName(it)
            },
            placeholder = {
                Text("Отчество", color = Color(0xFF939396), fontSize = 14.sp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            interactionSource = remember {
                MutableInteractionSource()
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF5F5F9),
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color(0xFFB8C1CC),
                unfocusedIndicatorColor = Color(0xFFEBEBEB),

                )
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = state.value.firstName,
            onValueChange = {
                viewModel.setFirstName(it)
            },
            placeholder = {
                Text("Фамилия", color = Color(0xFF939396), fontSize = 14.sp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            interactionSource = remember {
                MutableInteractionSource()
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF5F5F9),
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color(0xFFB8C1CC),
                unfocusedIndicatorColor = Color(0xFFEBEBEB),

                )
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = state.value.date,
            onValueChange = {
                viewModel.setDate(it)
            },
            placeholder = {
                Text("Дата рождения", color = Color(0xFF939396), fontSize = 14.sp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            interactionSource = remember {
                MutableInteractionSource()
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF5F5F9),
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color(0xFFB8C1CC),
                unfocusedIndicatorColor = Color(0xFFEBEBEB),

                )
        )
        Spacer(modifier = Modifier.height(24.dp))
        val isExpanded = remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color(0xFFF5F5F9), shape = RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEBEBEB), shape = RoundedCornerShape(10.dp))
                .clickable {
                    isExpanded.value = true
                }
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                state.value.gender,
                color = Color(0xFF939396),
                modifier = Modifier
                    .fillMaxWidth()
            )
            DropdownMenu(modifier = Modifier.fillMaxWidth(), properties = PopupProperties(
                dismissOnBackPress = true
            ), expanded = isExpanded.value, onDismissRequest = { isExpanded.value = false }) {
                DropdownMenuItem(onClick = {
                    viewModel.setGender("Мужской")
                    isExpanded.value = false
                }) {
                    Text("Мужской")
                }
                DropdownMenuItem(onClick = {
                    viewModel.setGender("Женский")
                    isExpanded.value = false
                }) {
                    Text("Женский")
                }
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                viewModel.submit()
                navController.navigate(Screen.MainScreen.route)
            },
            elevation = elevation(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF1A6FEE),
                disabledBackgroundColor = Color(0xFFC9D4FB),
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text("Создать", fontWeight = FontWeight.W600, fontSize = 17.sp, color = Color.White)
        }
    }
}

@Composable
private fun AppBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 20.dp, end = 12.dp
            ), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Создание карты\nпациента",
            fontSize = 24.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.weight(1f)
        )
        TextButton(
            onClick = { navController.navigate(Screen.MainScreen.route) },
            modifier = Modifier.weight(0.6f)
        ) {
            Text("Пропустить", color = Color(0xFF1A6FEE), fontSize = 15.sp)
        }
    }
}