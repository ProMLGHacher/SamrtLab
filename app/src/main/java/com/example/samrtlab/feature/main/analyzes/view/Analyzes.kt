package com.example.samrtlab.feature.main.analyzes.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.samrtlab.R
import com.example.samrtlab.consts.elevation
import com.example.samrtlab.domain.model.catalog.CatalogItem
import com.example.samrtlab.domain.model.news.NewsItem
import com.example.samrtlab.feature.main.analyzes.view_model.CatalogViewModel
import com.example.samrtlab.feature.main.analyzes.view_model.NewsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerDefaults
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlin.random.Random

@ExperimentalMaterialApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalGlideComposeApi
@Composable
fun Analyzes(
    mainNavController: NavController,
    appNavController: NavController,
    viewModel: NewsViewModel = hiltViewModel(),
    catalogViewModel: CatalogViewModel = hiltViewModel(),
    setSheetState: (state: ModalBottomSheetValue) -> Unit,
    setSheetContent: (@Composable (() -> Unit)) -> Unit
) {
    val catalogState = catalogViewModel.state.collectAsState()
    val state = viewModel.state.collectAsState()

    val refreshState = rememberPullRefreshState(
        refreshing = catalogState.value.catalogIsLoading || catalogState.value.categoriesIsLoading || state.value.isLoading,
        onRefresh = {
            viewModel.update()
            catalogViewModel.updateCategories()
        })

    Box(modifier = Modifier.fillMaxSize()) {
        PullRefreshIndicator(
            catalogState.value.catalogIsLoading || catalogState.value.categoriesIsLoading || state.value.isLoading,
            refreshState,
            Modifier
                .align(Alignment.TopCenter)
                .zIndex(10000f)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
        ) {
            item {
                AppBar()
                if (state.value.isLoading) NewsSkeleton() else News(news = state.value.news)
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Каталог анализов",
                    color = Color(0xFF939396),
                    fontWeight = FontWeight.W900,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Categories(
                    isLoading = catalogState.value.categoriesIsLoading,
                    categories = catalogState.value.categories,
                    selected = catalogState.value.selectedCategory,
                    selectCategory = {
                        catalogViewModel.setCategory(it)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (catalogState.value.catalogIsLoading)
                items(10) {
                    SkeletonCatalogItem()
                }
            else
                items(catalogState.value.catalog) {
                    CatalogItem(
                        item = it,
                        setSheetState = setSheetState,
                        setSheetContent = setSheetContent
                    )
                }
        }
    }
}

@Composable
fun BottomSheet(
    item: CatalogItem,
    closeSheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                item.name,
                maxLines = 2,
                fontWeight = FontWeight.W900,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color(0xFFB8C1CC), CircleShape)
                    .clip(CircleShape)
                    .clickable { closeSheet.invoke() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.8f),
                    tint = Color(0xFF7E7E9A)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Описание", color = Color(0xFF939396), fontWeight = FontWeight.W500, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(item.description, color = Color.Black, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Подготовка",
            color = Color(0xFF939396),
            fontWeight = FontWeight.W500,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(item.preparation, color = Color.Black, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(44.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Результаты через:", color = Color(0xFF939396), fontWeight = FontWeight.W900)
                Text(
                    item.time_result,
                    color = Color.Black,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Биоматериал:", color = Color(0xFF939396), fontWeight = FontWeight.W900)
                Text(item.bio, color = Color.Black, fontWeight = FontWeight.W500, fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            elevation = elevation(),
            shape = RoundedCornerShape(10.dp),
            onClick = { /*TODO*/ }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF1A6FEE)
            )
        ) {
            Text("Добавить за ${item.price} ₽", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.W900)
        }
    }
}

@Composable
fun SkeletonCatalogItem() {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp, top = 12.dp)
            .shadow(
                20.dp,
                spotColor = Color(0xFFE4E8F5).copy(0.6f),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                brush = SolidColor(Color(0xFFF4F4F4)),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(20.dp)
                    .background(
                        Color.Black.copy(0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(20.dp)
                    .background(
                        Color.Black.copy(0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(50.dp)
                            .background(
                                Color(0xFF939396).copy(0.1f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .height(22.dp)
                            .width(50.dp)
                            .background(
                                Color.Black.copy(0.1f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp)
                        .background(
                            Color(0xFF1A6FEE),
                            RoundedCornerShape(10.dp)
                        )
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CatalogItem(
    item: CatalogItem,
    setSheetState: (state: ModalBottomSheetValue) -> Unit,
    setSheetContent: (@Composable (() -> Unit)) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp, top = 12.dp)
            .shadow(
                20.dp,
                spotColor = Color(0xFFE4E8F5).copy(0.6f),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                brush = SolidColor(Color(0xFFF4F4F4)),
                shape = RoundedCornerShape(10.dp),
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                setSheetContent.invoke {
                    BottomSheet(item = item, closeSheet = {
                        setSheetState.invoke(ModalBottomSheetValue.Hidden)
                    })
                }
                setSheetState.invoke(ModalBottomSheetValue.Expanded)
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                item.name,
                maxLines = 2,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.75f),
                color = Color.Black,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        item.time_result,
                        color = Color(0xFF939396),
                        fontWeight = FontWeight.W900
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        item.price + " ₽",
                        color = Color.Black,
                        fontWeight = FontWeight.W900,
                        fontSize = 17.sp
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF1A6FEE)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Добавить", color = Color.White, fontWeight = FontWeight.W900)
                }
            }
        }
    }
}

@Composable
fun Categories(
    isLoading: Boolean,
    categories: List<String>,
    selected: String?,
    selectCategory: (String) -> Unit
) {
    if (isLoading)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF5F5F9), shape = RoundedCornerShape(10.dp))
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .padding(vertical = 14.dp, horizontal = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(22.dp)
                            .width(100.dp)
                            .background(
                                Color(0xFF7E7E9A).copy(0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                    }
                }
            }
        }
    else
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(categories.size) {
                Box(
                    modifier = Modifier
                        .background(
                            if (selected == categories[it]) Color(0xFF1A6FEE) else Color(
                                0xFFF5F5F9
                            ), shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            selectCategory(categories[it])
                        }
                        .padding(vertical = 14.dp, horizontal = 20.dp)
                ) {
                    Text(
                        categories[it],
                        color = if (selected == categories[it]) Color.White else Color(0xFF7E7E9A),
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
}


@ExperimentalSnapperApi
@ExperimentalGlideComposeApi
@ExperimentalPagerApi
@Composable
fun NewsSkeleton() {
    Text(
        "Акции и новости",
        color = Color(0xFF939396),
        fontWeight = FontWeight.W900,
        fontSize = 17.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    val scrollState = rememberPagerState()
    val size = LocalConfiguration.current.screenWidthDp
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
    ) {
        @Composable
        fun text() {
            Box(
                modifier = Modifier
                    .width((size * 0.7f).dp)
                    .height(20.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(0.2f),
                                Color.White.copy(0.1f)
                            ),
                            start = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        ),
                        RoundedCornerShape(10.dp)
                    )
            )
        }
        items(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color.Gray,
                                Color.Gray.copy(0.5f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ),
                        RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        text()
                        Spacer(modifier = Modifier.height(4.dp))
                        text()
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        text()
                        Spacer(modifier = Modifier.height(4.dp))
                        text()
                        Spacer(modifier = Modifier.height(4.dp))
                        text()
                        Spacer(modifier = Modifier.height(4.dp))
                        text()
                    }
                }
            }
        }
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
            .padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))

    val size = LocalConfiguration.current.screenWidthDp
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(news.size) {
            val color = remember {
                mutableStateOf(Random.nextLong())
            }
            Box(
                modifier = Modifier
                    .width((size * 0.7f).dp)
                    .height(160.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(color.value).copy(1f),
                                Color(color.value).copy(0.5f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ),
                        RoundedCornerShape(10.dp)
                    )
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        news[it].name,
                        fontWeight = FontWeight.W900,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            news[it].description,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            news[it].price + " ₽",
                            color = Color.White,
                            fontWeight = FontWeight.W900,
                            fontSize = 20.sp
                        )
                    }
                }
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