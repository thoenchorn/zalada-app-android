package com.thoen.zaladaandroid.presentation.main.model

import androidx.annotation.DrawableRes

data class NavItem(
    val title: String,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int,
)
