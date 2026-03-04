package com.example.toeic_app.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getThemeMode(): Flow<Boolean>

    suspend fun setThemeMode(isLightMode: Boolean)
}