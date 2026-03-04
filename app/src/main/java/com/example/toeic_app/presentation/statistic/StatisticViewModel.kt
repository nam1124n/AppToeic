package com.example.toeic_app.presentation.statistic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor() : ViewModel() {

    // Dummy data for spider chart
    // Order: Overall, Listening, Reading, Writing, Speaking, Vocabulary, Grammar
    private val _chartData = MutableStateFlow(
        listOf(
            0.5f, 0.7f, 0.4f, 0.2f, 0.3f, 0.6f, 0.8f
        )
    )
    val chartData = _chartData.asStateFlow()
}
