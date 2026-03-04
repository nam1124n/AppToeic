package com.example.toeic_app.di

import android.content.Context
import com.example.toeic_app.data.repository.practice.WritingRepositoryImpl
import com.example.toeic_app.domain.repository.practice.WritingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WritingModule {

    @Provides
    @Singleton
    fun provideWritingRepository(
        @ApplicationContext context: Context,
        firestore: com.google.firebase.firestore.FirebaseFirestore
    ): WritingRepository {
        return WritingRepositoryImpl(context, firestore)
    }
}
