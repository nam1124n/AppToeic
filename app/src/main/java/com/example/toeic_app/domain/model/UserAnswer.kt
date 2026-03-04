package com.example.toeic_app.domain.model

data class UserAnswer(
    val questionId: String,
    val selectedOptionId: String,
    val timestamp: Long = System.currentTimeMillis()
)
