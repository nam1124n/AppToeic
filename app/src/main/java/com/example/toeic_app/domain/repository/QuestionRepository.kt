package com.example.toeic_app.domain.repository

import com.example.toeic_app.domain.model.Question
import com.example.toeic_app.domain.model.UserAnswer
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    suspend fun getQuestions(): List<Question>
    suspend fun saveUserAnswer(userAnswer: UserAnswer)
    suspend fun getUserAnswer(questionId: String): UserAnswer?
    fun getAllUserAnswers(): Flow<Map<String, UserAnswer>> // Helper to observe all answers if needed
}
