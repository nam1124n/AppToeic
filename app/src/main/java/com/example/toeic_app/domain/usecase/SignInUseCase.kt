package com.example.toeic_app.domain.usecase

import com.example.toeic_app.domain.model.User
import com.example.toeic_app.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.signIn(email, password)
    }
}
