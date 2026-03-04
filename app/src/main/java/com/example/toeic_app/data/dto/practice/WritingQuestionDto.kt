package com.example.toeic_app.data.dto.practice

import com.example.toeic_app.domain.model.practice.WritingQuestion

data class WritingQuestionDto(
    val testId: Int = 0,
    val id: Int = 0,
    val questionNumber: Int = 0,
    val imageUrl: String? = null,
    val keywords: String = "",
    val sampleAnswer: String? = null
)

fun WritingQuestion.toDto(): WritingQuestionDto {
    return WritingQuestionDto(
        testId = this.testId,
        id = this.id,
        questionNumber = this.questionNumber,
        imageUrl = this.imageUrl,
        keywords = this.keywords,
        sampleAnswer = this.sampleAnswer
    )
}

fun WritingQuestionDto.toDomain(): WritingQuestion {
    return WritingQuestion(
        testId = this.testId,
        id = this.id,
        questionNumber = this.questionNumber,
        imageUrl = this.imageUrl,
        keywords = this.keywords,
        sampleAnswer = this.sampleAnswer
    )
}
