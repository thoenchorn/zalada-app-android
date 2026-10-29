package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.ProductRepository

class SearchProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(query: String, categoryId: String? = null): List<Product> =
        productRepository.searchProducts(query, categoryId)
}
