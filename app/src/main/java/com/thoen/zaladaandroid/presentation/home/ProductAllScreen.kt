package com.thoen.zaladaandroid.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.core.common.UiState
import com.thoen.zaladaandroid.presentation.component.ButtonBack
import com.thoen.zaladaandroid.presentation.component.ProductGrid
import com.thoen.zaladaandroid.presentation.component.ShapeIcons
import com.thoen.zaladaandroid.presentation.component.horizontalPadding
import com.thoen.zaladaandroid.presentation.component.rememberWindowWidth
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.navigation.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAllScreen(
    navController: NavController,
    viewModel: ProductAllViewModel = viewModel(factory = ProductAllViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val windowWidth = rememberWindowWidth()

    Scaffold(
        containerColor = androidx.compose.ui.graphics.Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Products",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    ButtonBack(onClick = { navController.popBackStack() })
                },
                actions = {
                    ShapeIcons(
                        onClick = { navController.navigate(Search.route) },
                        icon = com.thoen.zaladaandroid.R.drawable.search_light
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                )
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is UiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )

                is UiState.Error -> Text(
                    text = state.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )

                is UiState.Success -> ProductGrid(
                    products = state.data,
                    contentPadding = PaddingValues(
                        start = windowWidth.horizontalPadding,
                        end = windowWidth.horizontalPadding,
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
