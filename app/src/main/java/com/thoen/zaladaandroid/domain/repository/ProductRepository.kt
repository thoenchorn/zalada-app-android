package com.thoen.zaladaandroid.domain.repository

import com.thoen.zaladaandroid.domain.model.Category
import com.thoen.zaladaandroid.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getNewArrivals(): List<Product>
    suspend fun getPopularProducts(): List<Product>
    suspend fun searchProducts(query: String, categoryId: String?): List<Product>
    suspend fun getProductById(id: Int): Product?
    suspend fun getCategories(): List<Category>
}
