@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.samrtlab.feature.main.profile.view

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.samrtlab.R
import com.example.samrtlab.core.CustomButton
import com.example.samrtlab.core.CustomDropDown
import com.example.samrtlab.core.CustomTextField
import com.example.samrtlab.feature.main.profile.view_model.ProfileViewModel

@Composable
fun Profile(
    appNavController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state) {
        Log.e("ddsdd", state.toString())
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    LaunchedEffect(imageUri) {
        imageUri?.let {
            viewModel.setImageUri(imageUri)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            "Карта пациента",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.W700
        )
        Spacer(modifier = Modifier.height(7.dp))
        Box(
            modifier = Modifier
                .height(123.dp)
                .width(148.dp)
                .background(Color(0xFFD9D9D9).copy(0.5f), shape = RoundedCornerShape(60.dp))
                .clip(
                    RoundedCornerShape(60.dp)
                )
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = state.imageUri ?: ImageBitmap.imageResource(id = R.drawable.camera).asAndroidBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(if (state.imageUri == null) 0.4f else 1f),
                contentScale = if (state.imageUri == null) ContentScale.Fit else ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Без карты пациента вы не сможете заказать анализы.\nВ картах пациентов будут храниться результаты анализов вас и ваших близких.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = Color(0xFF939396)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = state.name,
            onValueChange = {
                viewModel.setName(it)
            },
            placeholder = "Имя"
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            value = state.firstName,
            onValueChange = {
                viewModel.setFirstName(it)
            },
            placeholder = "Фамилия"
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            value = state.lastName,
            onValueChange = {
                viewModel.setLastName(it)
            },
            placeholder = "Отчество"
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomTextField(
            value = state.date,
            onValueChange = {
                viewModel.setDate(it)
            },
            placeholder = "Дата"
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomDropDown(
            value = state.gender,
            items = listOf(
                "Мужской",
                "Женский"
            )
        ) {
            viewModel.setGender(it)
        }
        Spacer(modifier = Modifier.height(22.dp))
        val context = LocalContext.current
        CustomButton(text = "Сохранить") {
            viewModel.submit()
            Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show()
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}