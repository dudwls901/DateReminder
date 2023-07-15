package com.ongveloper.datereminder.di


import com.ongveloper.data.local.datasource.ScheduleLocalDataSource
import com.ongveloper.data.local.datasourceimpl.ScheduleLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindScheduleLocalDataSource(
        scheduleLocalDatasourceImpl: ScheduleLocalDataSourceImpl,
    ): ScheduleLocalDataSource

}