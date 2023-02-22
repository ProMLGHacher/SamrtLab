package com.example.samrtlab.feature.onboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.samrtlab.R
import com.example.samrtlab.feature.app.theme.blue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import com.example.samrtlab.feature.app.theme.green

data class OnboardItem(
    val title: String,
    val subTitle: String,
    val image: Int,
)

val items = listOf(
    OnboardItem(
        title = "Анализы",
        subTitle = "Экспресс сбор и получение проб",
        image = R.drawable.onboard_1
    ),
    OnboardItem(
        title = "Уведомления",
        subTitle = "Вы быстро узнаете о результатах",
        image = R.drawable.onboard_2
    ),
    OnboardItem(
        title = "Мониторинг",
        subTitle = "Наши врачи всегда наблюдают \n" +
                "за вашими показателями здоровья",
        image = R.drawable.onboard_3
    )
)

@Composable
fun Onboard() {
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar()
        LazyRow(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items.size
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        items[it].title,
                        color = green,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(items[it].subTitle, color = Color(0xFF939396))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF57A9FF).copy(0.5f),
                                    shape = CircleShape
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF57A9FF).copy(0.5f),
                                    shape = CircleShape
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF57A9FF).copy(0.5f),
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp)
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text("Пропустить", color = blue, fontWeight = FontWeight.W600, fontSize = 20.sp)
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                .background(
                    blue.copy(0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(0.7f),
                tint = Color.White
            )
        }
    }
}