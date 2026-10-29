package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.repository.CartRepository

class ToggleAllCartItemsUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(selected: Boolean) = cartRepository.setAllSelected(selected)
}
