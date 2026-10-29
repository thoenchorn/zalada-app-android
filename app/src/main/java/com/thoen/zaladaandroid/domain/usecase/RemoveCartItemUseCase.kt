package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.CartRepository

class RemoveCartItemUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: Int) = cartRepository.removeItem(productId)
}
