package com.thoen.zaladaandroid.domain.model

/**
 * A product sold by the store.
 *
 * [imageRes] is a drawable resource id so the sample app can work offline. Swap it for an
 * `imageUrl` once the catalogue comes from the network.
 */
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val originalPrice: Double,
    val discountPrice: Double,
    val imageRes: Int,
    val categoryId: String,
    val isNewArrival: Boolean = false,
)
