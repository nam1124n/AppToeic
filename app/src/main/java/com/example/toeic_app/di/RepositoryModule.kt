package com.example.toeic_app.di

import com.example.toeic_app.data.repository.AuthRepositoryImpl
import com.example.toeic_app.data.repository.QuestionRepositoryImpl
import com.example.toeic_app.domain.repository.AuthRepository
import com.example.toeic_app.domain.repository.QuestionRepository
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
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindQuestionRepository(
        questionRepositoryImpl: QuestionRepositoryImpl
    ): QuestionRepository
}
