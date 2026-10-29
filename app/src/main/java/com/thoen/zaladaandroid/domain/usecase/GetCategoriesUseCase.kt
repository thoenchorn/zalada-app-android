package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.Category
import com.thoen.zaladaandroid.domain.repository.ProductRepository

class GetCategoriesUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(): List<Category> = productRepository.getCategories()
}
