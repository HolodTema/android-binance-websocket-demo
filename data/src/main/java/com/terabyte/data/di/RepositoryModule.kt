package com.terabyte.data.di

import com.terabyte.data.repository.WebsocketRepositoryImpl
import com.terabyte.domain.repository.WebsocketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindWebsocketRepository(repositoryImpl: WebsocketRepositoryImpl): WebsocketRepository

}