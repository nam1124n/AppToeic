package com.example.toeic_app.presentation.practice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.GridView
import androidx.compose.ui.graphics.Color

@Composable
fun WritingQ1To5Screen(
    testId: Int = 1,
    questionNumber: Int,
    viewModel: WritingViewModel = hiltViewModel(),
    onSubmit: (String) -> Unit
) {
    var currentQuestion by remember { mutableIntStateOf(questionNumber) }
    var showOverview by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val savedAnswers by viewModel.savedAnswers.collectAsState()
    val startTimeMs = remember { System.currentTimeMillis() }

    LaunchedEffect(testId, currentQuestion) {
        viewModel.loadQuestion(testId, currentQuestion)
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Writing - Question $currentQuestion") }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (currentQuestion > 1) currentQuestion-- },
                    enabled = currentQuestion > 1
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                }

                Button(
                    onClick = { showOverview = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C6BC0)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.GridView, contentDescription = "Overview", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Overview", fontWeight = FontWeight.Bold)
                    }
                }

                IconButton(
                    onClick = { if (currentQuestion < 5) currentQuestion++ },
                    enabled = currentQuestion < 5
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            when (val state = uiState) {
                is WritingUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is WritingUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is WritingUiState.Success -> {
                    WritingContent(
                        testId = testId,
                        question = state.question,
                        initialAnswer = savedAnswers[currentQuestion] ?: "",
                        onAnswerChange = { viewModel.saveAnswer(currentQuestion, it) },
                        onSubmit = { answer ->
                            val duration = (System.currentTimeMillis() - startTimeMs) / 60000
                            viewModel.submitAnswer(duration, true)
                            onSubmit(answer)
                        }
                    )
                }
            }
        }

        if (showOverview) {
            com.example.toeic_app.presentation.writing.WritingOverviewDialog(
                questionRange = 1..5,
                answeredQuestions = savedAnswers.filter { it.value.isNotBlank() }.keys,
                onQuestionSelect = { currentQuestion = it },
                onDismissRequest = { showOverview = false }
            )
        }
    }
}

@Composable
fun WritingContent(
    testId: Int,
    question: com.example.toeic_app.domain.model.practice.WritingQuestion,
    initialAnswer: String,
    onAnswerChange: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    var answerText by remember(question.questionNumber) { mutableStateOf(initialAnswer) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showSampleAnswer by remember(question.questionNumber) { mutableStateOf(false) }
    val context = LocalContext.current
    val wordCount = answerText.trim().split("\\s+".toRegex()).count { it.isNotEmpty() }

    val pdfLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri ->
        if (uri != null) {
            com.example.toeic_app.utils.PdfHelper.generatePdf(
                context = context,
                uri = uri,
                title = "Test $testId",
                questionId = question.questionNumber,
                prompt = "Keywords: ${question.keywords}",
                answer = answerText
            )
            android.widget.Toast.makeText(context, "PDF Saved!", android.widget.Toast.LENGTH_SHORT).show()
        }
        onSubmit(answerText)
    }

    if (showSaveDialog) {
        com.example.toeic_app.presentation.writing.SaveSubmissionDialog(
            onDismiss = { showSaveDialog = false },
            onSavePdf = {
                showSaveDialog = false
                pdfLauncher.launch("TOEIC_Test${testId}_Q${question.questionNumber}.pdf")
            },
            onSubmitOnly = {
                showSaveDialog = false
                onSubmit(answerText)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Directions: Write a sentence based on the picture below.",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (question.questionNumber in 1..5 && question.imageUrl != null) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(question.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Question Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp)),
                loading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Failed to load image", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "Keywords: ${question.keywords}",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = answerText,
            onValueChange = { 
                answerText = it
                onAnswerChange(it) 
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            placeholder = { Text("Write your sentence here...") },
            maxLines = 10
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Words: $wordCount",
                style = MaterialTheme.typography.bodyMedium
            )

            Row {
                OutlinedButton(
                    onClick = { showSampleAnswer = !showSampleAnswer },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(if (showSampleAnswer) "Hide Idea" else "Show Idea")
                }

                Button(
                    onClick = { showSaveDialog = true },
                    enabled = answerText.isNotBlank()
                ) {
                    Text("Submit")
                }
            }
        }
        
        if (showSampleAnswer) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sample Answer:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = question.sampleAnswer ?: "",
                        color = Color(0xFF1B5E20)
                    )
                }
            }
        }
    }
}
