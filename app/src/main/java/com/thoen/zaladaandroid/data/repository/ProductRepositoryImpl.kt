package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.mapper.toDomain
import com.thoen.zaladaandroid.data.source.ProductLocalDataSource
import com.thoen.zaladaandroid.domain.model.Category
import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val dataSource: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun getProducts(): List<Product> =
        dataSource.getProducts().map { it.toDomain() }

    override suspend fun getNewArrivals(): List<Product> =
        dataSource.getNewArrivals().map { it.toDomain() }

    override suspend fun getPopularProducts(): List<Product> =
        dataSource.getPopularProducts().map { it.toDomain() }

    override suspend fun searchProducts(query: String, categoryId: String?): List<Product> =
        dataSource.searchProducts(query, categoryId).map { it.toDomain() }

    override suspend fun getProductById(id: Int): Product? =
        dataSource.getProductById(id)?.toDomain()

    override suspend fun getCategories(): List<Category> =
        dataSource.getCategories().map { it.toDomain() }
}
