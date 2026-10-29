package com.thoen.zaladaandroid.domain.repository

import com.thoen.zaladaandroid.domain.model.CartItem
import com.thoen.zaladaandroid.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun addToCart(product: Product, quantity: Int = 1)
    suspend fun updateQuantity(productId: Int, quantity: Int)
    suspend fun removeItem(productId: Int)
    suspend fun setSelected(productId: Int, selected: Boolean)
    suspend fun setAllSelected(selected: Boolean)
}
