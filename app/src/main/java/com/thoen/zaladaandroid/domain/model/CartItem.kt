package com.thoen.zaladaandroid.domain.model

data class CartItem(
    val product: Product,
    val quantity: Int = 1,
    val isChecked: Boolean = true,
) {
    val subtotal: Double get() = product.discountPrice * quantity
}
