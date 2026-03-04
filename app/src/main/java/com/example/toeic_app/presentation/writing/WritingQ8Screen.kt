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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toeic_app.presentation.practice.WritingViewModel
import kotlinx.coroutines.delay

import androidx.compose.runtime.collectAsState
import com.example.toeic_app.domain.model.practice.WritingQuestion
import com.example.toeic_app.presentation.practice.WritingUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingQ8Screen(
    testTitle: String = "Test 15",
    testId: Int = 1,
    viewModel: WritingViewModel = hiltViewModel(),
    onClose: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Writing prompt" to Icons.Default.Description, "Your response" to Icons.Default.Create)

    val uiState by viewModel.uiState.collectAsState()
    val savedAnswers by viewModel.savedAnswers.collectAsState()

    LaunchedEffect(testId) {
        viewModel.loadQuestion(testId, 8)
    }

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
                        Icon(Icons.Default.Create, contentDescription = "Premium", tint = Color(0xFFFFD700)) // Crown placeholder
                    }
                    IconButton(onClick = { /* Handle report click */ }) {
                        Icon(Icons.Default.Flag, contentDescription = "Report", tint = Color.Red)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background, titleContentColor = MaterialTheme.colorScheme.onBackground)
            )
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
                        androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is WritingUiState.Error -> {
                        Text(
                            text = state.message,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is WritingUiState.Success -> {
                        if (selectedTab == 0) {
                            WritingPromptTab(
                                prompt = state.question.keywords,
                                onSwipeRight = { selectedTab = 1 }
                            )
                        } else {
                            YourResponseTab(
                                testId = testId, 
                                question = state.question,
                                viewModel = viewModel,
                                initialAnswer = savedAnswers[8] ?: "",
                                onAnswerChange = { viewModel.saveAnswer(8, it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WritingPromptTab(prompt: String, onSwipeRight: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Question 1 of 1",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(end = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .heightIn(min = 80.dp)
                    .background(Color(0xFF1976D2))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = prompt,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Swipe right to write your response >>",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onSwipeRight() }
        )
    }
}

@Composable
fun YourResponseTab(
    testId: Int, 
    question: WritingQuestion,
    viewModel: WritingViewModel,
    initialAnswer: String,
    onAnswerChange: (String) -> Unit
) {
    var userResponse by remember { mutableStateOf(initialAnswer) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var showSampleAnswer by remember { mutableStateOf(false) }
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
                questionId = 8,
                prompt = question.keywords,
                answer = userResponse
            )
            android.widget.Toast.makeText(context, "PDF Saved!", android.widget.Toast.LENGTH_SHORT).show()
        }
        viewModel.submitAnswer(elapsedTimeSeconds.toLong() / 60, true)
        android.widget.Toast.makeText(context, "Submitted!", android.widget.Toast.LENGTH_SHORT).show()
        // TODO: Navigate to score screen here
    }

    if (showSaveDialog) {
        com.example.toeic_app.presentation.writing.SaveSubmissionDialog(
            onDismiss = { showSaveDialog = false },
            onSavePdf = {
                showSaveDialog = false
                pdfLauncher.launch("TOEIC_Test${testId}_Q8.pdf")
            },
            onSubmitOnly = {
                showSaveDialog = false
                viewModel.submitAnswer(elapsedTimeSeconds.toLong() / 60, true)
                android.widget.Toast.makeText(context, "Submitted!", android.widget.Toast.LENGTH_SHORT).show()
                // TODO: Navigate to score screen here
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
                focusedContainerColor = Color(0xFFF9F9F9),
                unfocusedContainerColor = Color(0xFFF9F9F9),
                disabledContainerColor = Color(0xFFF9F9F9)
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
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = "$wordCount / 500",
                fontSize = 14.sp,
                color = if (isOverLimit) Color.Red else Color.Gray,
                fontWeight = if (isOverLimit) FontWeight.Bold else FontWeight.Normal
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            androidx.compose.material3.OutlinedButton(
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

@Preview(showBackground = true)
@Composable
fun WritingQ8ScreenPreview() {
    WritingQ8Screen()
}
