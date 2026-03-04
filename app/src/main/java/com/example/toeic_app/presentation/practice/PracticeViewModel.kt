package com.example.toeic_app.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toeic_app.domain.usecase.question.GetQuestionsUseCase
import com.example.toeic_app.domain.usecase.question.GetUserAnswerUseCase
import com.example.toeic_app.domain.usecase.question.SelectAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val selectAnswerUseCase: SelectAnswerUseCase,
    private val getUserAnswerUseCase: GetUserAnswerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeUiState())
    val uiState: StateFlow<PracticeUiState> = _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val questions = getQuestionsUseCase()
            _uiState.update { 
                it.copy(
                    questions = questions,
                    isLoading = false
                ) 
            }
            // Ideally we also load existing answers here if needed, 
            // but for now we rely on the in-memory map or load lazily.
            // A robust solution would observe all answers from repo in a separate flow logic
            // or load them one by one. given the constraints, let's keep it simple.
            // However, to ensure UI is correct, we should probably fetch valid answers mapping.
            // Since repo is in-memory for this simple backbone, we can assume empty start or persistence.
            // If we want to restore answers, we might need getAllUserAnswersUseCase or similar.
            // For now, let's just make sure when we select, it updates.
        }
    }

    fun selectAnswer(questionId: String, optionId: String) {
        viewModelScope.launch {
            selectAnswerUseCase(questionId, optionId)
            
            // Update local state to reflect change immediately if necessary
            // Or ideally observe the repository if it exposes a Flow.
            // Here we manually update the map in UiState for immediate feedback
            val updatedMap = _uiState.value.userAnswers.toMutableMap()
            // We need to construct UserAnswer here or fetch it back.
            // Fetching back is safer.
            val answer = getUserAnswerUseCase(questionId)
            if (answer != null) {
                updatedMap[questionId] = answer
                 _uiState.update { it.copy(userAnswers = updatedMap) }
            }
        }
    }

    fun onNextQuestion() {
        _uiState.update { 
            if (!it.isLastQuestion) {
                it.copy(currentQuestionIndex = it.currentQuestionIndex + 1)
            } else it
        }
    }

    fun onPreviousQuestion() {
        _uiState.update { 
            if (!it.isFirstQuestion) {
                it.copy(currentQuestionIndex = it.currentQuestionIndex - 1)
            } else it
        }
    }
}
