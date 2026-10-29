package com.thoen.zaladaandroid.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.presentation.component.AppIcons
import com.thoen.zaladaandroid.presentation.component.GlassCard
import com.thoen.zaladaandroid.presentation.component.LabelAndAction
import com.thoen.zaladaandroid.presentation.component.ProductCard
import com.thoen.zaladaandroid.presentation.component.ShapeIcon
import com.thoen.zaladaandroid.presentation.component.WindowWidth
import com.thoen.zaladaandroid.presentation.component.horizontalPadding
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.theme.glassFillStrong
import com.thoen.zaladaandroid.presentation.home.component.BannerPager
import com.thoen.zaladaandroid.presentation.home.component.CategoryItem
import com.thoen.zaladaandroid.presentation.navigation.Cart
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.navigation.ProductAll
import com.thoen.zaladaandroid.presentation.navigation.Search

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val windowWidth = rememberWindowWidth()
    val horizontal = windowWidth.horizontalPadding
    val cardWidth = when (windowWidth) {
        WindowWidth.Compact -> 168.dp
        WindowWidth.Medium -> 184.dp
        WindowWidth.Expanded -> 208.dp
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            HomeHero(
                cartCount = uiState.cartCount,
                horizontalPadding = horizontal,
                onCartClick = { navController.navigate(Cart.route) },
                onSearchClick = { navController.navigate(Search.route) },
            )
        }

        item {
            Column(modifier = Modifier.padding(horizontal = horizontal)) {
                LabelAndAction(
                    label = "Categories",
                    onClick = { navController.navigate(ProductAll.route) }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                    items(uiState.categories, key = { it.id }) { category ->
                        CategoryItem(
                            onClick = { navController.navigate(Search.route) },
                            icon = AppIcons.category(category.id),
                            title = category.name
                        )
                    }
                }
            }
        }

        if (uiState.newArrivals.isNotEmpty()) {
            item {
                ProductRow(
                    title = "New Arrival",
                    products = uiState.newArrivals,
                    cardWidth = cardWidth,
                    horizontalPadding = horizontal,
                    showBadge = true,
                    onAddToCart = viewModel::onAddToCart,
                    onSeeAll = { navController.navigate(ProductAll.route) },
                    onProductClick = { product ->
                        navController.navigate(DetailProduct.createRoute(product.id))
                    },
                )
            }
        }

        if (uiState.popularProducts.isNotEmpty()) {
            item {
                ProductRow(
                    title = "Popular Products",
                    products = uiState.popularProducts,
                    cardWidth = cardWidth,
                    horizontalPadding = horizontal,
                    showBadge = false,
                    onAddToCart = viewModel::onAddToCart,
                    onSeeAll = { navController.navigate(ProductAll.route) },
                    onProductClick = { product ->
                        navController.navigate(DetailProduct.createRoute(product.id))
                    },
                )
            }
        }

        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun HomeHero(
    cartCount: Int,
    horizontalPadding: androidx.compose.ui.unit.Dp,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
            .padding(top = 12.dp),
        shape = MaterialTheme.shapes.extraLarge,
        glow = true,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(glassFillStrong())
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = "Location",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Phnom Penh",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                ShapeIcon(
                    onClick = onCartClick,
                    icon = R.drawable.buy,
                    count = cartCount
                )
                Spacer(modifier = Modifier.width(10.dp))
                ShapeIcon(
                    onClick = onSearchClick,
                    icon = R.drawable.search_light
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "Find best device\nfor your setup!",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(20.dp))
            BannerPager()
        }
    }
}

@Composable
private fun ProductRow(
    title: String,
    products: List<Product>,
    cardWidth: androidx.compose.ui.unit.Dp,
    horizontalPadding: androidx.compose.ui.unit.Dp,
    showBadge: Boolean,
    onAddToCart: (Product) -> Unit,
    onSeeAll: () -> Unit,
    onProductClick: (Product) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LabelAndAction(
            label = title,
            onClick = onSeeAll,
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(products, key = { it.id }) { product ->
                ProductCard(
                    product = product,
                    modifier = Modifier.width(cardWidth),
                    showBadge = showBadge,
                    onAddToCart = { onAddToCart(product) },
                    onClick = { onProductClick(product) },
                )
            }
        }
    }
}
