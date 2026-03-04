package com.example.toeic_app.data.repository

import com.example.toeic_app.data.local.FakeQuestionDataSource
import com.example.toeic_app.domain.model.Question
import com.example.toeic_app.domain.model.UserAnswer
import com.example.toeic_app.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val dataSource: FakeQuestionDataSource
) : QuestionRepository {

    override suspend fun getQuestions(): List<Question> {
        return dataSource.getQuestions()
    }

    override suspend fun saveUserAnswer(userAnswer: UserAnswer) {
        dataSource.saveUserAnswer(userAnswer)
    }

    override suspend fun getUserAnswer(questionId: String): UserAnswer? {
        return dataSource.getUserAnswer(questionId)
    }

    override fun getAllUserAnswers(): Flow<Map<String, UserAnswer>> {
        return dataSource.userAnswers
    }
}
