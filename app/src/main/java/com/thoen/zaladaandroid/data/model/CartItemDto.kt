package com.thoen.zaladaandroid.data.model

data class CartItemDto(
    val productId: Int,
    val quantity: Int = 1,
    val isChecked: Boolean = true,
)
