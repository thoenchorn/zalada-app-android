package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.Product
import com.thoen.zaladaandroid.domain.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(product: Product, quantity: Int = 1) =
        cartRepository.addToCart(product, quantity)
}
