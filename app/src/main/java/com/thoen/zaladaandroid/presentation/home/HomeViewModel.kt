package com.thoen.zaladaandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thoen.zaladaandroid.core.di.ServiceLocator
import com.thoen.zaladaandroid.domain.model.Category
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.usecase.AddToCartUseCase
import com.thoen.zaladaandroid.domain.usecase.GetCategoriesUseCase
import com.thoen.zaladaandroid.domain.usecase.GetNewArrivalsUseCase
import com.thoen.zaladaandroid.domain.usecase.GetPopularProductsUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveCartCountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val categories: List<Category> = emptyList(),
    val newArrivals: List<Product> = emptyList(),
    val popularProducts: List<Product> = emptyList(),
    val cartCount: Int = 0,
    val isLoading: Boolean = true,
)

class HomeViewModel(
    private val getCategories: GetCategoriesUseCase,
    private val getNewArrivals: GetNewArrivalsUseCase,
    private val getPopularProducts: GetPopularProductsUseCase,
    private val addToCart: AddToCartUseCase,
    observeCartCount: ObserveCartCountUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCatalog()
        viewModelScope.launch {
            observeCartCount().collect { count ->
                _uiState.update { it.copy(cartCount = count) }
            }
        }
    }

    private fun loadCatalog() {
        viewModelScope.launch {
            runCatching {
                val categories = getCategories()
                val newArrivals = getNewArrivals()
                val popularProducts = getPopularProducts()
                Triple(categories, newArrivals, popularProducts)
            }.onSuccess { (categories, newArrivals, popularProducts) ->
                _uiState.update {
                    it.copy(
                        categories = categories,
                        newArrivals = newArrivals,
                        popularProducts = popularProducts,
                        isLoading = false,
                    )
                }
            }.onFailure {
                _uiState.update { state -> state.copy(isLoading = false) }
            }
        }
    }

    fun onAddToCart(product: Product) {
        viewModelScope.launch { addToCart(product) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    ServiceLocator.getCategoriesUseCase,
                    ServiceLocator.getNewArrivalsUseCase,
                    ServiceLocator.getPopularProductsUseCase,
                    ServiceLocator.addToCartUseCase,
                    ServiceLocator.observeCartCountUseCase,
                )
            }
        }
    }
}
