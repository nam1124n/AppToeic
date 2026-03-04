package com.example.toeic_app.domain.repository

import com.example.toeic_app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String): Result<User>
    fun signOut()
    fun getCurrentUser(): User?
    fun getAuthState(): Flow<User?>
}
