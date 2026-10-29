package com.thoen.zaladaandroid.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.component.AppIcons
import com.thoen.zaladaandroid.presentation.component.ButtonBack
import com.thoen.zaladaandroid.presentation.component.ProductGrid
import com.thoen.zaladaandroid.presentation.component.horizontalPadding
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.theme.NeonButtonGradientHorizontal
import com.thoen.zaladaandroid.presentation.theme.glassBorder
import com.thoen.zaladaandroid.presentation.theme.glassFill

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val horizontal = rememberWindowWidth().horizontalPadding
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontal, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonBack(onClick = { navController.popBackStack() })
                Spacer(modifier = Modifier.width(12.dp))
                TextField(
                    value = uiState.query,
                    onValueChange = viewModel::onQueryChange,
                    placeholder = { Text("Search products") },
                    singleLine = true,
                    shape = CircleShape,
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.search_light),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    },
                    trailingIcon = {
                        if (uiState.query.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    viewModel.onQueryChange("")
                                    keyboardController?.hide()
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "Clear",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                                )
                            }
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = glassFill(),
                        unfocusedContainerColor = glassFill(),
                        focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                        disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = horizontal),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(uiState.tabs) { index, tab ->
                    SearchChip(
                        title = tab.title,
                        icon = AppIcons.category(tab.id),
                        selected = uiState.selectedTabIndex == index,
                        onClick = { viewModel.onTabSelected(index) },
                    )
                }
            }

            if (uiState.results.isEmpty()) {
                EmptyResults()
            } else {
                ProductGrid(
                    products = uiState.results,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 6.dp),
                    contentPadding = PaddingValues(
                        start = horizontal,
                        end = horizontal,
                        top = 8.dp,
                        bottom = 24.dp,
                    ),
                    onProductClick = { productId ->
                        navController.navigate(DetailProduct.createRoute(productId))
                    },
                    onAddToCart = viewModel::onAddToCart,
                )
            }
        }
    }
}

@Composable
private fun SearchChip(
    title: String,
    @androidx.annotation.DrawableRes icon: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val shape = CircleShape
    Row(
        modifier = Modifier
            .clip(shape)
            .then(
                if (selected) {
                    Modifier.background(NeonButtonGradientHorizontal)
                } else {
                    Modifier.background(glassFill())
                }
            )
            .border(
                width = 1.dp,
                color = if (selected) androidx.compose.ui.graphics.Color.Transparent else glassBorder(),
                shape = shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun EmptyResults() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.search_bulk),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "No products found",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Try another keyword or category.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
