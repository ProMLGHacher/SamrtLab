package com.example.samrtlab.feature.main.analyzes.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.samrtlab.R
import com.example.samrtlab.core.elevation
import com.example.samrtlab.domain.model.catalog.CatalogItem
import com.example.samrtlab.domain.model.news.NewsItem
import com.example.samrtlab.feature.cart.view_model.CartViewModel
import com.example.samrtlab.feature.main.analyzes.model.NewsState
import com.example.samrtlab.feature.main.analyzes.model.CatalogState
import com.example.samrtlab.feature.main.analyzes.view_model.CatalogViewModel
import com.example.samrtlab.feature.main.analyzes.view_model.NewsViewModel
import com.example.samrtlab.feature.navigation.model.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlin.random.Random

@ExperimentalAnimationApi
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
    setSheetContent: (@Composable (() -> Unit)) -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val catalogState = catalogViewModel.state.collectAsState()
    val state = viewModel.state.collectAsState()
    val allCatalog = catalogViewModel.allList.collectAsState()

    val refreshState =
        rememberPullRefreshState(refreshing = catalogState.value.catalogIsLoading || catalogState.value.categoriesIsLoading || state.value.isLoading,
            onRefresh = {
                viewModel.update()
                catalogViewModel.updateCategories()
            })

    Column(modifier = Modifier.fillMaxSize()) {
        AppBar(
            catalogState.value.searchText
        ) {
            catalogViewModel.setSearchText(it)
        }
        AnimatedContent(targetState = catalogState.value.searchText.isEmpty(), transitionSpec = {
            fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300, 300))
        }) {
            if (catalogState.value.searchText.isEmpty())
                Main(
                    appNavController = appNavController,
                    catalogState = catalogState.value,
                    state = state.value,
                    setSheetState = setSheetState,
                    setSheetContent = setSheetContent,
                    catalogViewModel = catalogViewModel,
                    refreshState = refreshState,
                    allCatalog,
                    cartViewModel
                )
            else Search(
                allCatalog.value,
                catalogState.value.searchText,
                setSheetState,
                setSheetContent,
                cartViewModel
            )
        }
    }

}

@ExperimentalMaterialApi
@Composable
fun Search(
    value: List<CatalogItem>,
    searchText: String,
    setSheetState: (state: ModalBottomSheetValue) -> Unit,
    setSheetContent: (@Composable (() -> Unit)) -> Unit,
    cartViewModel: CartViewModel
) {
    val focus = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = Color(0xFFF4F4F4)
        )
        value.filter { it.name.contains(searchText, ignoreCase = true) }.forEach {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focus.clearFocus()
                    setSheetContent.invoke {
                        BottomSheet(item = it, closeSheet = {
                            setSheetState.invoke(ModalBottomSheetValue.Hidden)

                        }, cartViewModel)
                    }
                    setSheetState.invoke(ModalBottomSheetValue.Expanded)
                }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 20.dp),
                    verticalAlignment = CenterVertically
                ) {
                    Text(it.name, modifier = Modifier.weight(2f), fontSize = 15.sp)
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text(it.price + " ₽", fontSize = 17.sp)
                        Text(it.time_result, fontSize = 14.sp, color = Color(0xFF939396))
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(1.dp),
                    color = Color(0xFFF4F4F4)
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalGlideComposeApi
@ExperimentalSnapperApi
@ExperimentalMaterialApi
@Composable
fun Main(
    appNavController: NavController,
    catalogState: CatalogState,
    state: NewsState,
    setSheetState: (state: ModalBottomSheetValue) -> Unit,
    setSheetContent: (@Composable () -> Unit) -> Unit,
    catalogViewModel: CatalogViewModel,
    refreshState: PullRefreshState,
    allCatalog: State<List<CatalogItem>>,
    cartViewModel: CartViewModel
) {
    val scrollState = rememberLazyListState()
    val scrollUp = remember {
        mutableStateOf(false)
    }
    val cartState = cartViewModel.state.collectAsState()
    LaunchedEffect(scrollState.canScrollBackward) {
        scrollUp.value = scrollState.canScrollBackward
    }
    LaunchedEffect(key1 = catalogState.selectedCategory) {
        if (allCatalog.value.isNotEmpty()) {
            if (!scrollState.isScrollInProgress) {
                scrollState.scrollToItem(allCatalog.value.indexOf(allCatalog.value.find { it.category == catalogState.selectedCategory }))
            }
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }.collect {
            catalogViewModel.setFirstVisibleIndex(it)
        }
    }
    LaunchedEffect(key1 = true) {
        cartViewModel.updateCart()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        PullRefreshIndicator(
            catalogState.catalogIsLoading || catalogState.categoriesIsLoading || state.isLoading,
            refreshState,
            Modifier
                .align(TopCenter)
                .zIndex(10000f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(refreshState)
                .verticalScroll(rememberScrollState())
        ) {
            AnimatedVisibility(
                visible = !scrollUp.value,
                enter = slideInVertically(
                    animationSpec = tween(durationMillis = 180, easing = LinearEasing)
                ) + expandVertically(
                    animationSpec = tween(durationMillis = 180, easing = LinearEasing)
                ),
                exit = slideOutVertically(
                    animationSpec = tween(durationMillis = 180, easing = LinearEasing),
                ) + shrinkVertically(
                    animationSpec = tween(durationMillis = 180, easing = LinearEasing),
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        if (state.isLoading) NewsSkeleton() else News(news = state.news)
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
                    }
                }
            }
            Categories(isLoading = catalogState.categoriesIsLoading,
                categories = catalogState.categories,
                selected = catalogState.selectedCategory,
                selectCategory = {
                    catalogViewModel.setCategory(it)
                })
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier.weight(1f), state = scrollState
            ) {
                if (catalogState.catalogIsLoading) items(10) {
                    SkeletonCatalogItem()
                }
                else items(allCatalog.value) {
                    CatalogItem(
                        item = it,
                        cartViewModel,
                        setSheetState = setSheetState,
                        setSheetContent = setSheetContent,
                    )
                }
            }
            if (!cartState.value.isLoading) {
                AnimatedVisibility(visible = cartState.value.cart.isNotEmpty()) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFFA0A0A0).copy(0.25f)
                        )
                        Button(
                            onClick = {
                                appNavController.navigate(Screen.CartScreen.route)
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
                                .padding(vertical = 24.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.cart),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        "В корзину",
                                        color = Color.White,
                                        fontWeight = FontWeight.W600,
                                        fontSize = 17.sp
                                    )
                                }
                                Text(
                                    cartState.value.sum.toString() + " ₽",
                                    fontWeight = FontWeight.W600,
                                    fontSize = 17.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheet(
    item: CatalogItem, closeSheet: () -> Unit, cartViewModel: CartViewModel
) {
    val cartState = cartViewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
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
            "Подготовка", color = Color(0xFF939396), fontWeight = FontWeight.W500, fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(item.preparation, color = Color.Black, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(44.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
            onClick = {
                if (cartState.value.cart.any { it.name == item.name }) cartViewModel.removeCartItem(
                    cartState.value.cart.single {
                        it.name == item.name
                    }
                ) else cartViewModel.add(item)
            },
            modifier = Modifier
                .border(
                    width = if (cartState.value.cart.any { item.name == it.name }) 1.dp else 0.dp,
                    color = Color(0xFF1A6FEE),
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (cartState.value.cart.any { item.name == it.name }) Color.Transparent else Color(
                    0xFF1A6FEE
                )
            )
        ) {
            Text(
                if (cartState.value.cart.any { item.name == it.name }) "Убрать" else "Добавить за ${item.price} ₽",
                color = if (cartState.value.cart.any { item.name == it.name }) Color(0xFF1A6FEE) else Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.W900
            )
        }
    }
}

@Composable
fun SkeletonCatalogItem() {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 12.dp, top = 12.dp)
            .shadow(
                20.dp, spotColor = Color(0xFFE4E8F5).copy(0.6f), shape = RoundedCornerShape(10.dp)
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
                        Color.Black.copy(0.1f), shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(20.dp)
                    .background(
                        Color.Black.copy(0.1f), shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(50.dp)
                            .background(
                                Color(0xFF939396).copy(0.1f), shape = RoundedCornerShape(4.dp)
                            )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .height(22.dp)
                            .width(50.dp)
                            .background(
                                Color.Black.copy(0.1f), shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp)
                        .background(
                            Color(0xFF1A6FEE), RoundedCornerShape(10.dp)
                        )
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CatalogItem(
    item: CatalogItem,
    cartViewModel: CartViewModel,
    setSheetState: (state: ModalBottomSheetValue) -> Unit,
    setSheetContent: (@Composable () -> Unit) -> Unit
) {
    val st = cartViewModel.state.collectAsState()
    Box(modifier = Modifier
        .padding(start = 20.dp, end = 20.dp, bottom = 12.dp, top = 12.dp)
        .shadow(
            20.dp, spotColor = Color(0xFFE4E8F5).copy(0.6f), shape = RoundedCornerShape(10.dp)
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
                }, cartViewModel)
            }
            setSheetState.invoke(ModalBottomSheetValue.Expanded)
        }) {
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        item.time_result, color = Color(0xFF939396), fontWeight = FontWeight.W900
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        item.price + " ₽",
                        color = Color.Black,
                        fontWeight = FontWeight.W900,
                        fontSize = 17.sp
                    )
                }
                val color = animateColorAsState(
                    targetValue = if (st.value.cart.any { it.name == item.name }) Color.Transparent else Color(
                        0xFF1A6FEE
                    )
                )
                Button(
                    onClick = {
                        if (st.value.cart.any { it.name == item.name }) cartViewModel.removeCartItem(
                            st.value.cart.single {
                                it.name == item.name
                            }
                        ) else cartViewModel.add(item)
                    },
                    elevation = elevation(),
                    modifier = Modifier
                        .border(
                            width = if (st.value.cart.any { it.name == item.name }) 1.dp else (-1).dp,
                            brush = SolidColor(Color(0xFF1A6FEE)),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .height(42.dp)
                        .width(120.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = color.value
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        if (st.value.cart.any { it.name == item.name }) "Убрать" else "Добавить",
                        textAlign = TextAlign.Center,
                        color = if (st.value.cart.any { it.name == item.name }) Color(0xFF1A6FEE) else Color.White,
                        fontWeight = FontWeight.W900
                    )
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
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = selected) {
        if (!isLoading) {
            if (categories.isNotEmpty()) {
                scrollState.animateScrollToItem(categories.indexOf(selected))
            }
        }
    }
    if (isLoading) LazyRow(
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
                            Color(0xFF7E7E9A).copy(0.2f), shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
    else LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        state = scrollState
    ) {
        items(categories.size) {
            Box(modifier = Modifier
                .background(
                    if (selected == categories[it]) Color(0xFF1A6FEE) else Color(
                        0xFFF5F5F9
                    ), shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    selectCategory(categories[it])
                }
                .padding(vertical = 14.dp, horizontal = 20.dp)) {
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
                                Color.White.copy(0.2f), Color.White.copy(0.1f)
                            ),
                            start = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                            end = Offset(0f, 0f)
                        ), RoundedCornerShape(10.dp)
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
                                Color.Gray, Color.Gray.copy(0.5f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ), RoundedCornerShape(10.dp)
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
                    .height(145.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(color.value).copy(1f), Color(color.value).copy(0.5f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                        ), RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                GlideImage(
                    model = news[it].image, contentDescription = null, modifier = Modifier.align(
                        Alignment.CenterEnd
                    ), contentScale = ContentScale.FillHeight
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
                        fontSize = 19.sp,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            news[it].description,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp
                        )
                        Text(
                            news[it].price + " ₽",
                            color = Color.White,
                            fontWeight = FontWeight.W900,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBar(
    text: String, onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
            .zIndex(101010f),
        placeholder = {
            Text("Искать анализы", color = Color(0xFF939396))
        },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = null)
        },
        trailingIcon = {
            if (text.isNotEmpty()) IconButton(onClick = {
                onValueChange("")
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color(0xFF7E7E9A)
                )
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFF5F5F9),
            focusedIndicatorColor = Color(0xFFEBEBEB),
            unfocusedIndicatorColor = Color(0xFFEBEBEB)
        )
    )
}
