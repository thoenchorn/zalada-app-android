package com.thoen.zaladaandroid.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thoen.zaladaandroid.core.di.ServiceLocator
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.usecase.AddToCartUseCase
import com.thoen.zaladaandroid.domain.usecase.GetPopularProductsUseCase
import com.thoen.zaladaandroid.domain.usecase.GetProductByIdUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveIsFavoriteUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailProductUiState(
    val product: Product? = null,
    val recommendedProducts: List<Product> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
)

class DetailProductViewModel(
    private val productId: Int,
    private val getProductById: GetProductByIdUseCase,
    private val getPopularProducts: GetPopularProductsUseCase,
    private val addToCart: AddToCartUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    observeIsFavorite: ObserveIsFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailProductUiState())
    val uiState: StateFlow<DetailProductUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val product = runCatching { getProductById(productId) }.getOrNull()
            val recommended = runCatching { getPopularProducts() }.getOrDefault(emptyList())
                .filterNot { it.id == productId }
                .take(5)
            _uiState.update {
                it.copy(product = product, recommendedProducts = recommended, isLoading = false)
            }
        }
        viewModelScope.launch {
            observeIsFavorite(productId).collect { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch { toggleFavorite(productId) }
    }

    fun onAddToCart() {
        val product = _uiState.value.product ?: return
        viewModelScope.launch { addToCart(product) }
    }

    companion object {
        fun factory(productId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DetailProductViewModel(
                    productId = productId,
                    getProductById = ServiceLocator.getProductByIdUseCase,
                    getPopularProducts = ServiceLocator.getPopularProductsUseCase,
                    addToCart = ServiceLocator.addToCartUseCase,
                    toggleFavorite = ServiceLocator.toggleFavoriteUseCase,
                    observeIsFavorite = ServiceLocator.observeIsFavoriteUseCase,
                )
            }
        }
    }
}
