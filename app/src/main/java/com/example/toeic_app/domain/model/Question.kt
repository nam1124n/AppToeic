package com.example.toeic_app.domain.model

data class Question(
    val id: String,
    val partType: PartType,
    val questionText: String,
    val imageUrl: String? = null, // For Part 1 primarily
    val audioUrl: String? = null, // For listening parts
    val options: List<AnswerOption>,
    val correctAnswerId: String
)

data class AnswerOption(
    val id: String,
    val text: String,
    val label: String // e.g., "A", "B", "C", "D"
)
