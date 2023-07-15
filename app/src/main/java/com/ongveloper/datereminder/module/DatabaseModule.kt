package com.ongveloper.datereminder.module

import android.content.Context
import androidx.room.Room
import com.ongveloper.data.local.SCHEDULE_DB_NAME
import com.ongveloper.data.local.ScheduleDatabase
import com.ongveloper.data.local.dao.ScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ScheduleDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ScheduleDatabase::class.java,
            SCHEDULE_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideScheduleDao(
        scheduleDatabase: ScheduleDatabase,
    ): ScheduleDao {
        return scheduleDatabase.scheduleDao()
    }
}