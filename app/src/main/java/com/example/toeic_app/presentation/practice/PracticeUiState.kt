package com.example.toeic_app.presentation.practice

import com.example.toeic_app.domain.model.Question
import com.example.toeic_app.domain.model.UserAnswer

data class PracticeUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val isLoading: Boolean = false,
    val userAnswers: Map<String, UserAnswer> = emptyMap()
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val isFirstQuestion: Boolean
        get() = currentQuestionIndex == 0

    val isLastQuestion: Boolean
        get() = questions.isNotEmpty() && currentQuestionIndex == questions.size - 1
        
    val progress: Float
        get() = if (questions.isEmpty()) 0f else (currentQuestionIndex + 1) / questions.size.toFloat()
}
