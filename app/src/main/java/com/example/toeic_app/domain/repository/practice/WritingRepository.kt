package com.example.toeic_app.domain.repository.practice

import com.example.toeic_app.domain.model.practice.WritingQuestion

interface WritingRepository {
    suspend fun getWritingQuestion(testId: Int, questionNumber: Int): WritingQuestion?
    suspend fun getAllQuestions(): List<WritingQuestion>
    suspend fun insertQuestion(question: WritingQuestion): Result<Unit>
    suspend fun updateQuestion(question: WritingQuestion): Result<Unit>
    suspend fun deleteQuestion(id: Int): Result<Unit>
    suspend fun migrateData(): Result<Unit> // for initial JSON -> Firestore migration
}
