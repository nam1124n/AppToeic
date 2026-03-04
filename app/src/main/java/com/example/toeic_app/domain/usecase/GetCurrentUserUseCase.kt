package com.example.toeic_app.domain.usecase

import com.example.toeic_app.domain.model.User
import com.example.toeic_app.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): User? {
        return repository.getCurrentUser()
    }
}
