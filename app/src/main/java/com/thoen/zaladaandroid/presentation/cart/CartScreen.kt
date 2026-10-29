package com.thoen.zaladaandroid.presentation.cart

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.component.AlertDialogCustom
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.component.ButtonBack
import com.thoen.zaladaandroid.domain.model.CartItem
import com.thoen.zaladaandroid.presentation.cart.component.ItemInCart
import com.thoen.zaladaandroid.presentation.home.component.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var productToDelete by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Cart",
                        color = Color.Black,
                        fontWeight = FontWeight.W600,
                        fontSize = 24.sp
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)

                    ) {
                        ButtonBack(
                            onClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                },
            )

        },
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(0.dp),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Grand Total: ${uiState.total}\$",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = {
                                //tap to c
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2D3C52),
                            )

                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "Checkout",
                                    color = Color.White,
                                    style = typography.bodyLarge,
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    text = "(${uiState.selectedCount})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                        }

                    }
                },
                tonalElevation = 0.dp,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Checkbox(
                        checked = uiState.allSelected,
                        onCheckedChange = { viewModel.onSelectAll(it) },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            checkedColor = Color(0xFF2D3C52),
                            uncheckedColor = Color(0xFF2D3C52)
                        )
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = "Select All Products",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                uiState.items.forEach { item ->
                    ItemInCart(
                        title = item.product.name,
                        originalPrice = item.product.originalPrice,
                        discountPrice = item.product.discountPrice,
                        image = item.product.imageRes,
                        quantity = item.quantity,
                        isChecked = item.isChecked,
                        onCheckedChange = { checked ->
                            viewModel.onSelect(item.product.id, checked)
                        },
                        onDeleted = {
                            productToDelete = item.product.id
                        },
                        onPlus = {
                            viewModel.onIncrease(item.product.id, item.quantity)
                        },
                        onMinus = {
                            viewModel.onDecrease(item.product.id, item.quantity)
                        }
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = "Recommended for you",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    uiState.recommendedProducts.forEach { product ->
                        ProductCard(
                            product = product,
                            end = true,
                            onClick = {
                                navController.navigate(DetailProduct.createRoute(product.id))
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

    productToDelete?.let { productId ->
        AlertDialogCustom(
            onDismissRequest = {
                productToDelete = null
            },
            onConfirmation = {
                viewModel.onRemove(productId)
                productToDelete = null
            },
            dialogTitle = "Delete Product",
            dialogText = "Are you sure you want to delete this product?",
        )
    }
}
