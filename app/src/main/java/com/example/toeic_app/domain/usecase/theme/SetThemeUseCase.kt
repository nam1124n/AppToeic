package com.example.toeic_app.domain.usecase.theme

import com.example.toeic_app.domain.repository.ThemeRepository

class SetThemeUseCase(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(isLightMode: Boolean){
        repository.setThemeMode(isLightMode)
    }
}