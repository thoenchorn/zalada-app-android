package com.thoen.zaladaandroid.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.core.common.UiState
import com.thoen.zaladaandroid.presentation.component.ButtonBack
import com.thoen.zaladaandroid.presentation.component.ShapeIcons
import com.thoen.zaladaandroid.presentation.component.ProductGrid
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.navigation.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAllScreen(
    navController: NavController,
    viewModel: ProductAllViewModel = viewModel(factory = ProductAllViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Product",
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
                actions = {
                    ShapeIcons(
                        onClick = {
                            navController.navigate(Search.route)
                        },
                        icon = R.drawable.search_light
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                }
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
                    .fillMaxHeight(),
            ) {
                when (val state = uiState) {
                    is UiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    is UiState.Error -> Text(text = state.message)

                    is UiState.Success -> ProductGrid(
                        products = state.data,
                        onProductClick = { productId ->
                            navController.navigate(DetailProduct.createRoute(productId))
                        },
                    )
                }
            }
        }

    }

}
