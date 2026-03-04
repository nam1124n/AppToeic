package com.example.toeic_app.domain.usecase.question

import com.example.toeic_app.domain.model.UserAnswer
import com.example.toeic_app.domain.repository.QuestionRepository
import javax.inject.Inject

class SelectAnswerUseCase @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(questionId: String, selectedOptionId: String) {
        val userAnswer = UserAnswer(
            questionId = questionId,
            selectedOptionId = selectedOptionId
        )
        repository.saveUserAnswer(userAnswer)
    }
}
