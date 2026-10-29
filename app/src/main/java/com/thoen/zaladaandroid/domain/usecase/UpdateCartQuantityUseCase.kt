package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.CartRepository

class UpdateCartQuantityUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: Int, quantity: Int) =
        cartRepository.updateQuantity(productId, quantity)
}
