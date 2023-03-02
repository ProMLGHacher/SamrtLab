package com.example.samrtlab.feature.main.profile.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Profile(
    appNavController: NavController
) {
    var imageUri = remember { mutableStateOf<Uri?>(null) } // UPDATE
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Карта пациента",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700
            )
            Spacer(modifier = Modifier.height(7.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .height(123.dp)
                    .width(148.dp)
                    .background(Color(0xFFD9D9D9).copy(0.5f), shape = RoundedCornerShape(60.dp))
                    .clip(
                        RoundedCornerShape(60.dp)
                    )
                    .clickable {

                    }
            ) {
                Image(
                    painter = painterResource(id = com.example.samrtlab.R.drawable.camera),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }
        }
    }
}