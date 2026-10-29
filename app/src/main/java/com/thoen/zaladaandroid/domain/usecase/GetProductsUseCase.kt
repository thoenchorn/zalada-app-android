package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(): List<Product> = productRepository.getProducts()
}
