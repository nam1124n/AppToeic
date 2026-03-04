package com.example.toeic_app.di

import android.app.Application
import androidx.room.Room
import com.example.toeic_app.data.local.db.ToeicDatabase
import com.example.toeic_app.data.repository.streak.StreakRepositoryImpl
import com.example.toeic_app.domain.repository.streak.StreakRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideToeicDatabase(app: Application): ToeicDatabase {
        return Room.databaseBuilder(
            app,
            ToeicDatabase::class.java,
            ToeicDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideStreakRepository(db: ToeicDatabase): StreakRepository {
        return StreakRepositoryImpl(db.streakDao)
    }
}
