package com.thoen.zaladaandroid.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoen.zaladaandroid.R
import com.thoen.zaladaandroid.presentation.component.ButtonBack
import com.thoen.zaladaandroid.presentation.component.ProductGrid
import com.thoen.zaladaandroid.presentation.navigation.DetailProduct
import com.thoen.zaladaandroid.presentation.search.model.SearchTab

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp)
            ) {
                ButtonBack(
                    onClick = {
                        navController.popBackStack()
                    }
                )
                TextField(
                    value = uiState.query,
                    onValueChange = viewModel::onQueryChange,
                    label = { Text(text = "Search") },
                    placeholder = { Text(text = "Search Product") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedSuffixColor = Color.Blue,
                        focusedLabelColor = Color.Gray,
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.search_light),
                                contentDescription = ""
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color(0xFF495E57),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .background(Color(0xFFFFFFFF))
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ScrollableTabRow(
                selectedTabIndex = uiState.selectedTabIndex,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .background(Color.Red),
                edgePadding = 12.dp,
                divider = {
                    HorizontalDivider(
                        color = Color.Transparent
                    )
                },

                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[uiState.selectedTabIndex]),
                        color = Color.Transparent,
                    )
                }
            ) {
                uiState.tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Row(
                                verticalAlignment = CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = title.icon),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(100))
                                        .background(
                                            color = if (uiState.selectedTabIndex == index)
                                                Color.White else Color(0xFFF3F6FB)
                                        )
                                        .padding(4.dp)
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = title.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (uiState.selectedTabIndex == index)
                                        Color.Black else Color(0xFF495E57)
                                )
                            }
                        },
                        selected = uiState.selectedTabIndex == index,
                        onClick = { viewModel.onTabSelected(index) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = if (uiState.selectedTabIndex == index)
                                        listOf(
                                            Color(0xFF2D3C52),
                                            Color(0xFF2D3C52).copy(alpha = 0.5f)
                                        )
                                    else listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                                )
                            )
                            .border(
                                width = 1.dp,
                                color = if (uiState.selectedTabIndex == index)
                                    Color.Transparent
                                else Color(0xFF495E57),
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
            ProductGrid(
                products = uiState.results,
                onProductClick = { productId ->
                    navController.navigate(DetailProduct.createRoute(productId))
                },
            )
        }
    }
}
