package com.thoen.zaladaandroid.data.mapper

import com.thoen.zaladaandroid.data.model.NotificationDto
import com.thoen.zaladaandroid.domain.model.NotificationItem

fun NotificationDto.toDomain(): NotificationItem = NotificationItem(
    id = id,
    title = title,
    message = message,
    time = time,
    iconRes = iconRes,
)
