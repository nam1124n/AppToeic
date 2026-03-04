package com.example.toeic_app.domain.usecase.question

import com.example.toeic_app.domain.model.Question
import com.example.toeic_app.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(): List<Question> {
        return repository.getQuestions()
    }
}
