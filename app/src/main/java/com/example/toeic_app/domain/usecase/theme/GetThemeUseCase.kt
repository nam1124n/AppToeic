package com.example.toeic_app.domain.usecase.theme

import com.example.toeic_app.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(
    private  val repository: ThemeRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.getThemeMode()
    }

}