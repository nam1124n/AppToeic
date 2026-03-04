package com.example.toeic_app.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toeic_app.domain.model.practice.WritingQuestion
import com.example.toeic_app.domain.repository.practice.WritingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminWritingViewModel @Inject constructor(
    private val repository: WritingRepository
) : ViewModel() {

    private val _questions = MutableStateFlow<List<WritingQuestion>>(emptyList())
    val questions: StateFlow<List<WritingQuestion>> = _questions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
            _questions.value = repository.getAllQuestions()
            _isLoading.value = false
        }
    }

    fun deleteQuestion(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.deleteQuestion(id)
            if (result.isSuccess) {
                loadQuestions()
            }
            _isLoading.value = false
        }
    }

    fun migrateData() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.migrateData()
            if (result.isSuccess) {
                loadQuestions()
            }
            _isLoading.value = false
        }
    }

    fun saveQuestion(
        testId: Int,
        id: Int,
        questionNumber: Int,
        imageUrl: String,
        keywords: String,
        sampleAnswer: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val question = WritingQuestion(
                testId = testId,
                id = id,
                questionNumber = questionNumber,
                imageUrl = imageUrl.ifBlank { null },
                keywords = keywords,
                sampleAnswer = sampleAnswer.ifBlank { null }
            )
            val exists = _questions.value.any { it.id == id }
            if (exists) {
                repository.updateQuestion(question)
            } else {
                repository.insertQuestion(question)
            }
            loadQuestions()
        }
    }
}
