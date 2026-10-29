package com.thoen.zaladaandroid.data.source

import com.thoen.zaladaandroid.data.model.CartItemDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartLocalDataSource {

    private val _items = MutableStateFlow(
        listOf(
            CartItemDto(productId = 2, quantity = 1, isChecked = true),
            CartItemDto(productId = 3, quantity = 1, isChecked = true),
            CartItemDto(productId = 4, quantity = 1, isChecked = true),
        )
    )
    val items: StateFlow<List<CartItemDto>> = _items.asStateFlow()

    fun add(productId: Int, quantity: Int) {
        _items.update { current ->
            val existing = current.firstOrNull { it.productId == productId }
            if (existing == null) {
                current + CartItemDto(productId = productId, quantity = quantity)
            } else {
                current.map {
                    if (it.productId == productId) it.copy(quantity = it.quantity + quantity) else it
                }
            }
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        _items.update { current ->
            current.map {
                if (it.productId == productId) it.copy(quantity = quantity.coerceAtLeast(1)) else it
            }
        }
    }

    fun remove(productId: Int) {
        _items.update { current -> current.filterNot { it.productId == productId } }
    }

    fun setSelected(productId: Int, selected: Boolean) {
        _items.update { current ->
            current.map { if (it.productId == productId) it.copy(isChecked = selected) else it }
        }
    }

    fun setAllSelected(selected: Boolean) {
        _items.update { current -> current.map { it.copy(isChecked = selected) } }
    }
}
