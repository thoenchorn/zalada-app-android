package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.CartRepository

class ToggleCartItemSelectionUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: Int, selected: Boolean) =
        cartRepository.setSelected(productId, selected)
}
