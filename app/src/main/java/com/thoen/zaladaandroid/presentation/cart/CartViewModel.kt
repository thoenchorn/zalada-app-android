package com.thoen.zaladaandroid.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thoen.zaladaandroid.core.di.ServiceLocator
import com.thoen.zaladaandroid.domain.model.CartItem
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.usecase.GetProductsUseCase
import com.thoen.zaladaandroid.domain.usecase.ObserveCartUseCase
import com.thoen.zaladaandroid.domain.usecase.RemoveCartItemUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleAllCartItemsUseCase
import com.thoen.zaladaandroid.domain.usecase.ToggleCartItemSelectionUseCase
import com.thoen.zaladaandroid.domain.usecase.UpdateCartQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val recommendedProducts: List<Product> = emptyList(),
) {
    val selectedItems: List<CartItem> get() = items.filter { it.isChecked }
    val total: Double get() = selectedItems.sumOf { it.subtotal }
    val selectedCount: Int get() = selectedItems.size
    val allSelected: Boolean get() = items.isNotEmpty() && items.all { it.isChecked }
}

class CartViewModel(
    observeCart: ObserveCartUseCase,
    getProducts: GetProductsUseCase,
    private val updateQuantity: UpdateCartQuantityUseCase,
    private val removeItem: RemoveCartItemUseCase,
    private val toggleSelection: ToggleCartItemSelectionUseCase,
    private val toggleAll: ToggleAllCartItemsUseCase,
) : ViewModel() {

    private val recommendedProducts = MutableStateFlow<List<Product>>(emptyList())

    val uiState: StateFlow<CartUiState> = combine(
        observeCart(),
        recommendedProducts,
    ) { items, recommended ->
        CartUiState(items = items, recommendedProducts = recommended)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState(),
        )

    init {
        viewModelScope.launch {
            recommendedProducts.value = runCatching { getProducts() }
                .getOrDefault(emptyList())
                .take(5)
        }
    }

    fun onIncrease(productId: Int, currentQuantity: Int) {
        viewModelScope.launch { updateQuantity(productId, currentQuantity + 1) }
    }

    fun onDecrease(productId: Int, currentQuantity: Int) {
        if (currentQuantity <= 1) return
        viewModelScope.launch { updateQuantity(productId, currentQuantity - 1) }
    }

    fun onRemove(productId: Int) {
        viewModelScope.launch { removeItem(productId) }
    }

    fun onSelect(productId: Int, selected: Boolean) {
        viewModelScope.launch { toggleSelection(productId, selected) }
    }

    fun onSelectAll(selected: Boolean) {
        viewModelScope.launch { toggleAll(selected) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CartViewModel(
                    observeCart = ServiceLocator.observeCartUseCase,
                    getProducts = ServiceLocator.getProductsUseCase,
                    updateQuantity = ServiceLocator.updateCartQuantityUseCase,
                    removeItem = ServiceLocator.removeCartItemUseCase,
                    toggleSelection = ServiceLocator.toggleCartItemSelectionUseCase,
                    toggleAll = ServiceLocator.toggleAllCartItemsUseCase,
                )
            }
        }
    }
}
