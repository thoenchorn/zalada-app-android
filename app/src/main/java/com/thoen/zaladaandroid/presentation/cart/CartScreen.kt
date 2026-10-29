package com.thoen.zaladaandroid.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.core.common.formatAsPrice
import com.thoen.zaladaandroid.presentation.cart.component.ItemInCart
import com.thoen.zaladaandroid.presentation.component.AlertDialogCustom
import com.thoen.zaladaandroid.presentation.component.ButtonBack
import com.thoen.zaladaandroid.presentation.component.GlassCard
import com.thoen.zaladaandroid.presentation.component.ProductCard
import com.thoen.zaladaandroid.presentation.component.WindowWidth
import com.thoen.zaladaandroid.presentation.component.horizontalPadding
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.theme.glassFillStrong

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val windowWidth = rememberWindowWidth()
    val horizontal = windowWidth.horizontalPadding
    val isExpanded = windowWidth == WindowWidth.Expanded
    var productToDelete by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Cart",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    ButtonBack(onClick = { navController.popBackStack() })
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                )
            )
        },
        bottomBar = {
            if (!isExpanded && uiState.items.isNotEmpty()) {
                CartSummaryBar(
                    total = uiState.total,
                    selectedCount = uiState.selectedCount,
                    horizontalPadding = horizontal,
                    onCheckout = { }
                )
            }
        },
    ) { innerPadding ->
        if (uiState.items.isEmpty()) {
            EmptyCart(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        } else if (isExpanded) {
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = horizontal),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                CartItems(
                    uiState = uiState,
                    viewModel = viewModel,
                    onDelete = { productToDelete = it },
                    onProductClick = {
                        navController.navigate(DetailProduct.createRoute(it))
                    },
                    modifier = Modifier.weight(1f)
                )
                CartSummaryCard(
                    total = uiState.total,
                    selectedCount = uiState.selectedCount,
                    onCheckout = { },
                    modifier = Modifier
                        .width(340.dp)
                        .padding(top = 8.dp)
                )
            }
        } else {
            CartItems(
                uiState = uiState,
                viewModel = viewModel,
                onDelete = { productToDelete = it },
                onProductClick = {
                    navController.navigate(DetailProduct.createRoute(it))
                },
                horizontalPadding = horizontal,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }

    productToDelete?.let { productId ->
        AlertDialogCustom(
            onDismissRequest = { productToDelete = null },
            onConfirmation = {
                viewModel.onRemove(productId)
                productToDelete = null
            },
            dialogTitle = "Delete Product",
            dialogText = "Are you sure you want to delete this product?",
        )
    }
}

@Composable
private fun CartItems(
    uiState: CartUiState,
    viewModel: CartViewModel,
    onDelete: (Int) -> Unit,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: androidx.compose.ui.unit.Dp = 0.dp,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.allSelected,
                    onCheckedChange = { viewModel.onSelectAll(it) },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.outline,
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Select all items",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        items(uiState.items, key = { it.product.id }) { item ->
            ItemInCart(
                title = item.product.name,
                originalPrice = item.product.originalPrice,
                discountPrice = item.product.discountPrice,
                image = item.product.imageRes,
                quantity = item.quantity,
                isChecked = item.isChecked,
                onCheckedChange = { viewModel.onSelect(item.product.id, it) },
                onDeleted = { onDelete(item.product.id) },
                onPlus = { viewModel.onIncrease(item.product.id, item.quantity) },
                onMinus = { viewModel.onDecrease(item.product.id, item.quantity) },
                modifier = Modifier.padding(horizontal = horizontalPadding)
            )
        }

        if (uiState.recommendedProducts.isNotEmpty()) {
            item {
                Text(
                    text = "You may also like",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(
                        start = horizontalPadding + 4.dp,
                        end = horizontalPadding + 4.dp,
                        top = 12.dp
                    )
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = horizontalPadding),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.recommendedProducts, key = { it.id }) { product ->
                        ProductCard(
                            product = product,
                            modifier = Modifier.width(160.dp),
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CartSummaryBar(
    total: Double,
    selectedCount: Int,
    horizontalPadding: androidx.compose.ui.unit.Dp,
    onCheckout: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
        shadowElevation = 20.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = horizontalPadding, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = total.formatAsPrice(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CheckoutButton(selectedCount = selectedCount, onClick = onCheckout)
        }
    }
}

@Composable
private fun CartSummaryCard(
    total: Double,
    selectedCount: Int,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GlassCard(
        shape = MaterialTheme.shapes.large,
        glow = true,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Order summary",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Selected items",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = selectedCount.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Grand total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = total.formatAsPrice(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text("Checkout ($selectedCount)", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun CheckoutButton(
    selectedCount: Int,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        modifier = Modifier.height(52.dp)
    ) {
        Text("Checkout ($selectedCount)", style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun EmptyCart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(glassFillStrong()),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.thoen.zaladaandroid.R.drawable.bag_light),
                contentDescription = null,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Browse the catalogue and add devices you like.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
