package com.example.toeic_app.domain.usecase.practice

import com.example.toeic_app.domain.model.practice.WritingQuestion
import com.example.toeic_app.domain.repository.practice.WritingRepository
import javax.inject.Inject

class GetWritingQuestionUseCase @Inject constructor(
    private val repository: WritingRepository
) {
    suspend operator fun invoke(testId: Int, questionNumber: Int): WritingQuestion? {
        return repository.getWritingQuestion(testId, questionNumber)
    }
}
