package com.thoen.zaladaandroid.data.model

data class ProductDto(
    val id: Int,
    val name: String,
    val description: String,
    val originalPrice: Double,
    val discountPrice: Double,
    val imageRes: Int,
    val categoryId: String,
    val isNewArrival: Boolean,
)
