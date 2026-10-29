package com.thoen.zaladaandroid.data.source

import com.thoen.zaladaandroid.data.model.NotificationDto

class NotificationLocalDataSource {

    private val notifications = List(10) { index ->
        NotificationDto(
            id = index,
            title = "Discount 50% for all products Discount 50% for all products Discount 50% for all products",
            message = "Discount 50% for all products Discount 50% for all products",
            time = "1 day ago",
        )
    }

    suspend fun getNotifications(): List<NotificationDto> = notifications
}
