package com.thoen.zaladaandroid.presentation.home


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.navigation.Cart
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.navigation.ProductAll
import com.thoen.zaladaandroid.presentation.navigation.Search
import com.thoen.zaladaandroid.presentation.home.component.Banner
import com.thoen.zaladaandroid.presentation.home.component.CategoryItem
import com.thoen.zaladaandroid.presentation.home.component.GridProductCard
import com.thoen.zaladaandroid.presentation.home.component.LabelAndAction
import com.thoen.zaladaandroid.presentation.home.component.ProductCard
import com.thoen.zaladaandroid.presentation.component.ShapeIcon

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight()

                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFD6C8A),
                                    Color(0xFFFDA56C)
                                ),
                            )
                        )
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.5f),
                                    Color.White
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        )
                ) {
                    Column {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ShapeIcon(
                                onClick = {},
                                icon = R.drawable.location
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Column {
                                Text(
                                    text = "Location",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Phnom Penh",
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            ShapeIcon(
                                onClick = {
                                    navController.navigate(Cart.route)
                                },
                                icon = R.drawable.buy,
                                count = uiState.cartCount
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            ShapeIcon(
                                onClick = {
                                    navController.navigate(Search.route)

                                },
                                icon = R.drawable.search_bold,
                            )
                        }
                        Text(
                            text = "Find best device for your setup!",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.W600,
                            lineHeight = 25.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        AutoScrollingLazyRow()
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                LabelAndAction(
                    label = "Categories",
                    onClick = {}
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    uiState.categories.forEach { category ->
                        CategoryItem(
                            onClick = {},
                            icon = category.iconRes,
                            title = category.name
                        )
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                LabelAndAction(
                    label = "New Arrival",
                    onClick = {
                        navController.navigate(ProductAll.route)
                    }
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    uiState.newArrivals.forEach { product ->
                        GridProductCard(
                            product = product,
                            onClick = {
                                navController.navigate(DetailProduct.createRoute(product.id))
                            },
                            onAddToCart = {
                                viewModel.onAddToCart(product)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                LabelAndAction(
                    label = "Popular Products",
                    onClick = {
                        navController.navigate(ProductAll.route)
                    }
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    uiState.popularProducts.forEach { product ->
                        ProductCard(
                            product = product,
                            end = true,
                            onClick = {
                                navController.navigate(DetailProduct.createRoute(product.id))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(100.dp))
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoScrollingLazyRow(
    modifier: Modifier = Modifier
) {
    var pageState = rememberPagerState (pageCount = { 5 })

    Box(
        modifier = modifier
            .wrapContentSize()
    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Banner()
            }
        }

    }
}
