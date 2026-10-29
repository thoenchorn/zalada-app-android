package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.source.FavoriteLocalDataSource
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.FavoriteRepository
import com.thoen.zaladaandroid.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val dataSource: FavoriteLocalDataSource,
    private val productRepository: ProductRepository,
) : FavoriteRepository {

    override fun observeFavorites(): Flow<List<Product>> =
        dataSource.productIds.map { ids ->
            ids.mapNotNull { productRepository.getProductById(it) }
        }

    override suspend fun toggleFavorite(productId: Int) = dataSource.toggle(productId)

    override fun observeIsFavorite(productId: Int): Flow<Boolean> =
        dataSource.productIds.map { ids -> productId in ids }
}
