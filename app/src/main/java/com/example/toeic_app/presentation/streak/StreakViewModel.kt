package com.example.toeic_app.presentation.streak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toeic_app.domain.model.streak.Streak
import com.example.toeic_app.domain.usecase.streak.GetStreakUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class StreakUiState {
    object Loading : StreakUiState()
    data class Success(val streak: Streak) : StreakUiState()
    data class Error(val message: String) : StreakUiState()
}

@HiltViewModel
class StreakViewModel @Inject constructor(
    getStreakUseCase: GetStreakUseCase
) : ViewModel() {

    val uiState: StateFlow<StreakUiState> = getStreakUseCase()
        .map { StreakUiState.Success(it) as StreakUiState }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StreakUiState.Loading
        )
}
