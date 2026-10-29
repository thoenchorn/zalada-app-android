package com.thoen.zaladaandroid.data.mapper

import com.thoen.zaladaandroid.data.model.CartItemDto
import com.thoen.zaladaandroid.domain.model.CartItem
import com.thoen.zaladaandroid.domain.model.Product

fun CartItemDto.toDomain(product: Product): CartItem = CartItem(
    product = product,
    quantity = quantity,
    isChecked = isChecked,
)
