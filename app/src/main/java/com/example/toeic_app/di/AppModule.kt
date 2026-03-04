package com.example.toeic_app.di

import android.content.Context
import com.example.toeic_app.data.repository.theme.ThemeRepositoryImpl
import com.example.toeic_app.domain.repository.ThemeRepository
import com.example.toeic_app.domain.usecase.theme.GetThemeUseCase
import com.example.toeic_app.domain.usecase.theme.SetThemeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): com.google.firebase.auth.FirebaseAuth {
        return com.google.firebase.auth.FirebaseAuth.getInstance()
    }
    
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): com.google.firebase.firestore.FirebaseFirestore {
        return com.google.firebase.firestore.FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideThemeRepository(
        @ApplicationContext context: Context
    ): ThemeRepository{
        return ThemeRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideGetThemeUseCase(repository: ThemeRepository): GetThemeUseCase{
        return GetThemeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetThemeUseCase(repository: ThemeRepository): SetThemeUseCase{
        return SetThemeUseCase(repository)
    }
    
}
