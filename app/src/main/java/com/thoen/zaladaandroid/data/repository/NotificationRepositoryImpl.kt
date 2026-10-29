package com.thoen.zaladaandroid.data.repository

import com.thoen.zaladaandroid.data.mapper.toDomain
import com.thoen.zaladaandroid.data.source.NotificationLocalDataSource
import com.thoen.zaladaandroid.domain.model.NotificationItem
import com.thoen.zaladaandroid.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val dataSource: NotificationLocalDataSource,
) : NotificationRepository {

    override suspend fun getNotifications(): List<NotificationItem> =
        dataSource.getNotifications().map { it.toDomain() }
}
