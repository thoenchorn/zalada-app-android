package com.thoen.zaladaandroid.data.mapper

import com.thoen.zaladaandroid.data.model.CategoryDto
import com.thoen.zaladaandroid.data.model.ProductDto
import com.thoen.zaladaandroid.domain.model.Category
import com.thoen.zaladaandroid.domain.model.Product

fun ProductDto.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description,
    originalPrice = originalPrice,
    discountPrice = discountPrice,
    imageRes = imageRes,
    categoryId = categoryId,
    isNewArrival = isNewArrival,
)

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    iconRes = iconRes,
)
