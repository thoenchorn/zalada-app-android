package com.thoen.zaladaandroid.domain.repository

import com.thoen.zaladaandroid.domain.model.NotificationItem

interface NotificationRepository {
    suspend fun getNotifications(): List<NotificationItem>
}
