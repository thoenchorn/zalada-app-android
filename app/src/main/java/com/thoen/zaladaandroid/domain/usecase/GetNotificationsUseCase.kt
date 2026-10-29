package com.thoen.zaladaandroid.domain.usecase

import com.thoen.zaladaandroid.domain.model.NotificationItem
import com.thoen.zaladaandroid.domain.repository.NotificationRepository

class GetNotificationsUseCase(private val notificationRepository: NotificationRepository) {
    suspend operator fun invoke(): List<NotificationItem> = notificationRepository.getNotifications()
}
