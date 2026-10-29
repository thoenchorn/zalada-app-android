package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.ProductRepository

class GetProductByIdUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(id: Int): Product? = productRepository.getProductById(id)
}
