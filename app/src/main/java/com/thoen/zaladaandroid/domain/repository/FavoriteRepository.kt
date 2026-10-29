package com.thoen.zaladaandroid.domain.repository

import com.thoen.zaladaandroid.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavorites(): Flow<List<Product>>
    suspend fun toggleFavorite(productId: Int)
    fun observeIsFavorite(productId: Int): Flow<Boolean>
}
