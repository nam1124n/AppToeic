package com.example.toeic_app.domain.usecase

import com.example.toeic_app.domain.model.User
import com.example.toeic_app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return repository.getAuthState()
    }
}
