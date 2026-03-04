package com.example.toeic_app.presentation.practice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toeic_app.presentation.practice.components.QuestionContent

@Composable
fun PracticeScreen(
    onNavigateBack: () -> Unit,
    viewModel: PracticeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Column {
                // Simple top bar placeholder or header
                if (uiState.questions.isNotEmpty()) {
                    LinearProgressIndicator(
                        progress = { uiState.progress },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                val currentQuestion = uiState.currentQuestion
                if (currentQuestion != null) {
                    val userAnswer = uiState.userAnswers[currentQuestion.id]
                    
                    QuestionContent(
                        question = currentQuestion,
                        selectedAnswerId = userAnswer?.selectedOptionId,
                        onAnswerSelected = { optionId ->
                            viewModel.selectAnswer(currentQuestion.id, optionId)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    
                    NavigationButtons(
                        isFirst = uiState.isFirstQuestion,
                        isLast = uiState.isLastQuestion,
                        onPrevious = viewModel::onPreviousQuestion,
                        onNext = viewModel::onNextQuestion
                    )
                } else {
                    Text("No questions available.")
                }
            }
        }
    }
}

@Composable
fun NavigationButtons(
    isFirst: Boolean,
    isLast: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        if (!isFirst) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Button(
            onClick = onNext,
            enabled = !isLast, // For now disable on last, could be "Finish"
            modifier = Modifier.weight(1f)
        ) {
            Text(if (isLast) "Finish" else "Next")
        }
    }
}
