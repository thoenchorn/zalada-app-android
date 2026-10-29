package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.mapper.toDomain
import com.thoen.zaladaandroid.data.source.CartLocalDataSource
import com.thoen.zaladaandroid.domain.model.CartItem
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.CartRepository
import com.thoen.zaladaandroid.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    private val dataSource: CartLocalDataSource,
    private val productRepository: ProductRepository,
) : CartRepository {

    override fun observeCart(): Flow<List<CartItem>> =
        dataSource.items.map { items ->
            items.mapNotNull { item ->
                productRepository.getProductById(item.productId)?.let { product ->
                    item.toDomain(product)
                }
            }
        }

    override suspend fun addToCart(product: Product, quantity: Int) =
        dataSource.add(product.id, quantity)

    override suspend fun updateQuantity(productId: Int, quantity: Int) =
        dataSource.updateQuantity(productId, quantity)

    override suspend fun removeItem(productId: Int) = dataSource.remove(productId)

    override suspend fun setSelected(productId: Int, selected: Boolean) =
        dataSource.setSelected(productId, selected)

    override suspend fun setAllSelected(selected: Boolean) = dataSource.setAllSelected(selected)
}
