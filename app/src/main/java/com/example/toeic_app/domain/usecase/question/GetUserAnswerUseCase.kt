package com.example.toeic_app.domain.usecase.question

import com.example.toeic_app.domain.model.UserAnswer
import com.example.toeic_app.domain.repository.QuestionRepository
import javax.inject.Inject

class GetUserAnswerUseCase @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(questionId: String): UserAnswer? {
        return repository.getUserAnswer(questionId)
    }
}
