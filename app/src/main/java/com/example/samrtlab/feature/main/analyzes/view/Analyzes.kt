package com.example.samrtlab.feature.main.analyzes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.samrtlab.R
import com.example.samrtlab.domain.model.news.NewsItem
import com.example.samrtlab.feature.main.analyzes.view_model.AnalyzesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlin.random.Random

@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalGlideComposeApi
@Composable
fun Analyzes(
    mainNavController: NavController,
    appNavController: NavController,
    viewModel: AnalyzesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        AppBar()
        News(news = state.value.news)
    }
}

@ExperimentalSnapperApi
@ExperimentalGlideComposeApi
@ExperimentalPagerApi
@Composable
fun News(
    news: List<NewsItem>
) {
    Text(
        "Акции и новости",
        color = Color(0xFF939396),
        fontWeight = FontWeight.W900,
        fontSize = 17.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    val scrollState = rememberPagerState()
    HorizontalPager(
        count = news.size,
        itemSpacing = 16.dp,
        state = scrollState,
        contentPadding = PaddingValues(start = 16.dp, end = 120.dp),
        flingBehavior = PagerDefaults.flingBehavior(
            state = scrollState,
            endContentPadding = PaddingValues(start = 16.dp, end = 120.dp).calculateEndPadding(LayoutDirection.Rtl),
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(Random.nextLong()).copy(1f), RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp)),
        ) {
            GlideImage(
                model = news[it].image,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.CenterEnd
                    ),
                contentScale = ContentScale.FillHeight
            )
            Column() {

            }
        }
    }
}

@Composable
private fun AppBar() {
    val text = remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        placeholder = {
            Text("Искать анализы", color = Color(0xFF939396))
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFF5F5F9),
            focusedIndicatorColor = Color(0xFFEBEBEB),
            unfocusedIndicatorColor = Color(0xFFEBEBEB)
        )
    )
}