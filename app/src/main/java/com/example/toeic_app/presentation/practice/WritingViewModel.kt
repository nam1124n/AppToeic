  package com.example.toeic_app.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toeic_app.domain.model.practice.WritingQuestion
import com.example.toeic_app.domain.usecase.practice.GetWritingQuestionUseCase
import com.example.toeic_app.domain.usecase.streak.CheckAndUpdateStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class WritingUiState {
    object Loading : WritingUiState()
    data class Success(val question: WritingQuestion) : WritingUiState()
    data class Error(val message: String) : WritingUiState()
}

@HiltViewModel
class WritingViewModel @Inject constructor(
    private val getWritingQuestionUseCase: GetWritingQuestionUseCase,
    private val checkAndUpdateStreakUseCase: CheckAndUpdateStreakUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WritingUiState>(WritingUiState.Loading)
    val uiState: StateFlow<WritingUiState> = _uiState.asStateFlow()

    private val _savedAnswers = MutableStateFlow<Map<Int, String>>(emptyMap())
    val savedAnswers: StateFlow<Map<Int, String>> = _savedAnswers.asStateFlow()

    fun saveAnswer(questionNumber: Int, answer: String) {
        _savedAnswers.update { currentMap ->
            val newMap = currentMap.toMutableMap()
            newMap[questionNumber] = answer
            newMap
        }
    }

    fun loadQuestion(testId: Int, questionNumber: Int) {
        viewModelScope.launch {
            _uiState.update { WritingUiState.Loading }
            try {
                val question = getWritingQuestionUseCase(testId, questionNumber)
                if (question != null) {
                    _uiState.update { WritingUiState.Success(question) }
                } else {
                    _uiState.update { WritingUiState.Error("Question not found") }
                }
            } catch (e: Exception) {
                _uiState.update { WritingUiState.Error(e.message ?: "An unexpected error occurred") }
            }
        }
    }

    fun submitAnswer(durationInMinutes: Long, testCompleted: Boolean = true) {
        viewModelScope.launch {
            checkAndUpdateStreakUseCase(durationInMinutes, testCompleted)
        }
    }
}
