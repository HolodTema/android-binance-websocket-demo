package com.terabyte.data.di

import com.terabyte.data.repository.WebsocketRepositoryImpl
import com.terabyte.data.storage.WebsocketStorage
import com.terabyte.data.storage.WebsocketStorageImpl
import com.terabyte.domain.repository.WebsocketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WebsocketModule {

    @Binds
    abstract fun bindWebsocketStorage(storage: WebsocketStorageImpl): WebsocketStorage

}