package com.ongveloper.datereminder.di


import com.ongveloper.data.repositoryimpl.ScheduleRepositoryImpl
import com.ongveloper.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl,
    ): ScheduleRepository

}