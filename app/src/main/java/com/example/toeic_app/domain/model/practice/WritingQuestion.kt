package com.example.toeic_app.domain.model.practice

data class WritingQuestion(
    val testId: Int,
    val id: Int,
    val questionNumber: Int,
    val imageUrl: String?,
    val keywords: String,
    val sampleAnswer: String? = null
)
