package com.example.toeic_app.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminWritingEditScreen(
    questionId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: AdminWritingViewModel = hiltViewModel()
) {
    val questions by viewModel.questions.collectAsState()
    val existingQ = questions.find { it.id == questionId }

    var testId by remember { mutableStateOf(existingQ?.testId?.toString() ?: "") }
    var id by remember { mutableStateOf(existingQ?.id?.toString() ?: "") }
    var questionNumber by remember { mutableStateOf(existingQ?.questionNumber?.toString() ?: "") }
    var keywords by remember { mutableStateOf(existingQ?.keywords ?: "") }
    var imageUrl by remember { mutableStateOf(existingQ?.imageUrl ?: "") }
    var sampleAnswer by remember { mutableStateOf(existingQ?.sampleAnswer ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (questionId == null) "Thêm câu hỏi" else "Sửa câu hỏi") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = testId,
                onValueChange = { testId = it },
                label = { Text("Test ID") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("Q ID (không đổi được nếu sửa)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = questionId == null
            )
            OutlinedTextField(
                value = questionNumber,
                onValueChange = { questionNumber = it },
                label = { Text("Question Number (1-8)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = keywords,
                onValueChange = { keywords = it },
                label = { Text("Nội dung câu hỏi / Keywords") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL (Cho câu 1-5)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = sampleAnswer,
                onValueChange = { sampleAnswer = it },
                label = { Text("Câu trả lời mẫu") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Button(
                onClick = {
                    val tId = testId.toIntOrNull() ?: 1
                    val qId = id.toIntOrNull() ?: (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
                    val qNum = questionNumber.toIntOrNull() ?: 1
                    viewModel.saveQuestion(tId, qId, qNum, imageUrl, keywords, sampleAnswer)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lưu lại")
            }
        }
    }
}
