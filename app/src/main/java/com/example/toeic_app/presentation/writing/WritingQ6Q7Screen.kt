package com.example.toeic_app.presentation.writing

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toeic_app.presentation.practice.WritingUiState
import com.example.toeic_app.presentation.practice.WritingViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingQ6Q7Screen(
    testId: Int = 1,
    questionNumber: Int = 6,
    viewModel: WritingViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    var currentQuestion by remember { mutableIntStateOf(questionNumber) }
    var showOverview by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val savedAnswers by viewModel.savedAnswers.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Writing prompt" to Icons.Default.Description, "Your response" to Icons.Default.Create)

    LaunchedEffect(testId, currentQuestion) {
        viewModel.loadQuestion(testId, currentQuestion)
    }

    val testTitle = "Test $testId"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(testTitle, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle premium click */ }) {
                        Icon(Icons.Default.Create, contentDescription = "Premium", tint = Color(0xFFFFD700))
                    }
                    IconButton(onClick = { /* Handle report click */ }) {
                        Icon(Icons.Default.Flag, contentDescription = "Report", tint = Color.Red)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background, titleContentColor = MaterialTheme.colorScheme.onBackground)
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
                    onClick = { if (currentQuestion > 6) currentQuestion-- },
                    enabled = currentQuestion > 6
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
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
                    onClick = { if (currentQuestion < 7) currentQuestion++ },
                    enabled = currentQuestion < 7
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                       SecondaryIndicator(
                           modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                           color = Color(0xFF1976D2)
                       )
                    }
                }
            ) {
                tabs.forEachIndexed { index, (title, icon) ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(title, fontWeight = FontWeight.SemiBold)
                            }
                        },
                        selectedContentColor = Color(0xFF1976D2),
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
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
                        if (selectedTab == 0) {
                            WritingQ6Q7PromptTab(
                                prompt = state.question.keywords,
                                questionNumber = state.question.questionNumber,
                                onSwipeRight = { selectedTab = 1 }
                            )
                        } else {
                            WritingQ6Q7ResponseTab(
                                testId = testId,
                                question = state.question,
                                viewModel = viewModel,
                                initialAnswer = savedAnswers[currentQuestion] ?: "",
                                onAnswerChange = { viewModel.saveAnswer(currentQuestion, it) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showOverview) {
        com.example.toeic_app.presentation.writing.WritingOverviewDialog(
            questionRange = 6..7,
            answeredQuestions = savedAnswers.filter { it.value.isNotBlank() }.keys,
            onQuestionSelect = { currentQuestion = it },
            onDismissRequest = { showOverview = false }
        )
    }
}

@Composable
fun WritingQ6Q7PromptTab(prompt: String, questionNumber: Int, onSwipeRight: () -> Unit) {
    // Q6 is 1 of 2, Q7 is 2 of 2
    val displayIndex = if (questionNumber == 6) 1 else if (questionNumber == 7) 2 else 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question $displayIndex of 2",
            color = Color(0xFF5C6BC0), // Based on user screenshot color
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .heightIn(min = 100.dp)
                    .background(Color(0xFF1976D2))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = prompt,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp
            )
        }
        
        // Faint dashed line separator like in screenshot
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(1.dp)
                .background(Color.LightGray)
        )

        Text(
            text = "Swipe right to write your response >>",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.clickable { onSwipeRight() }
        )
    }
}

@Composable
fun WritingQ6Q7ResponseTab(
    testId: Int,
    question: com.example.toeic_app.domain.model.practice.WritingQuestion,
    viewModel: WritingViewModel,
    initialAnswer: String,
    onAnswerChange: (String) -> Unit
) {
    var userResponse by remember(question.questionNumber) { mutableStateOf(initialAnswer) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showSampleAnswer by remember(question.questionNumber) { mutableStateOf(false) }
    var elapsedTimeSeconds by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val pdfLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri ->
        if (uri != null) {
            com.example.toeic_app.utils.PdfHelper.generatePdf(
                context = context,
                uri = uri,
                title = "Test $testId",
                questionId = question.questionNumber,
                prompt = question.keywords,
                answer = userResponse
            )
            android.widget.Toast.makeText(context, "PDF Saved!", android.widget.Toast.LENGTH_SHORT).show()
        }
        viewModel.submitAnswer(elapsedTimeSeconds.toLong() / 60, true)
        android.widget.Toast.makeText(context, "Submitted!", android.widget.Toast.LENGTH_SHORT).show()
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
                viewModel.submitAnswer(elapsedTimeSeconds.toLong() / 60, true)
                android.widget.Toast.makeText(context, "Submitted!", android.widget.Toast.LENGTH_SHORT).show()
            }
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            elapsedTimeSeconds++
        }
    }

    val minutes = elapsedTimeSeconds / 60
    val seconds = elapsedTimeSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)
    
    val wordCount = userResponse.trim().split("\\s+".toRegex()).count { it.isNotEmpty() }.let { if (userResponse.isBlank()) 0 else it }
    val isOverLimit = wordCount > 500

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "*Enter your answer here and click Submit to see sample answer and score. Do not leave answer blank and write more than 500 words.",
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Your response",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = userResponse,
            onValueChange = { 
                userResponse = it
                onAnswerChange(it) 
            },
            placeholder = { Text("Type your answer here...") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 250.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFFF9F9F9),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$timeFormatted taken",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                text = "$wordCount / 500",
                fontSize = 12.sp,
                color = if (isOverLimit) Color.Red else Color.Gray,
                fontWeight = if (isOverLimit) FontWeight.Bold else FontWeight.Normal
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = { showSampleAnswer = !showSampleAnswer },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(end = 8.dp)
            ) {
                Text(if (showSampleAnswer) "Hide Idea" else "Show Idea")
            }

            Button(
                onClick = {
                    showSaveDialog = true
                },
                enabled = userResponse.isNotBlank() && !isOverLimit,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0),
                    disabledContainerColor = Color(0xFFE0E0E0),
                    contentColor = Color.Gray
                )
            ) {
                Text("Submit", fontWeight = FontWeight.Bold)
            }
        }

        if (showSampleAnswer) {
            Spacer(modifier = Modifier.height(16.dp))
            androidx.compose.material3.Surface(
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
