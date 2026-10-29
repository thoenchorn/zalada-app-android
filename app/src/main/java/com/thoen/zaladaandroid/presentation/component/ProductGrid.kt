package com.thoen.zaladaandroid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thoen.zaladaandroid.domain.model.Product

@Composable
fun ProductGrid(
    products: List<Product>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onProductClick: (Int) -> Unit = {},
    onAddToCart: ((Product) -> Unit)? = null,
) {
    val windowWidth = rememberWindowWidth()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = windowWidth.productCardMinWidth),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                onAddToCart = onAddToCart?.let { { it(product) } },
                onClick = { onProductClick(product.id) },
            )
        }
    }
}
