package com.example.samrtlab.feature.change_mail.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.samrtlab.R
import com.example.samrtlab.feature.change_mail.view_model.ChangeMailViewModel

@Composable
fun ChangeMail(
    viewModel: ChangeMailViewModel = hiltViewModel(),
    navigateToConfirmEmail: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(70.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hand),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text("Добро пожаловать!", fontWeight = FontWeight.W700, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(23.dp))
        Text(
            "Войдите, чтобы пользоваться функциями приложения",
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            "Вход по E-mail",
            color = Color(0xFF7E7E9A),
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = state.value.mail,
            onValueChange = {
                           viewModel.changeEmail(it)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            placeholder = {
                Text("example@mail.ru", color = Color(0xFF000000).copy(0.5f))
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF5F5F9),
                focusedIndicatorColor = Color(0xFFEBEBEB),
                unfocusedIndicatorColor = Color(0xFFEBEBEB)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navigateToConfirmEmail()
            },
            enabled = state.value.isCorrect,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF1A6FEE),
                disabledBackgroundColor = Color(0xFFC9D4FB),
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text("Далее", fontWeight = FontWeight.W600, fontSize = 17.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Или войдите с помощью",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 15.sp
        )
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
                contentColor = Color.Black,
                disabledContentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            border = BorderStroke(width = 1.dp, Color(0xFFEBEBEB)),
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text("Войти с Яндекс", fontWeight = FontWeight.W500, fontSize = 17.sp)
        }
        Spacer(modifier = Modifier.height(56.dp))
    }
}