package com.example.toeic_app.presentation.writing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toeic_app.R

@Composable
fun WritingQuestionScreen(
    testId: Int = 1,
    questionNumber: Int = 1,
    onClose: () -> Unit = {}
) {
    val testTitle = "Test $testId"

    if (questionNumber in 1..5) {
        com.example.toeic_app.presentation.practice.WritingQ1To5Screen(
            testId = testId,
            questionNumber = questionNumber,
            onSubmit = { /* TODO logic handled later */ }
        )
    } else if (questionNumber in 6..7) {
        com.example.toeic_app.presentation.writing.WritingQ6Q7Screen(
            testId = testId,
            questionNumber = questionNumber,
            onClose = onClose
        )
    } else if (questionNumber == 8) {
        com.example.toeic_app.presentation.writing.WritingQ8Screen(
            testTitle = testTitle,
            testId = testId,
            onClose = onClose
        )
    } else {
        WritingQuestionScreenOld(
            testTitle = testTitle,
            onClose = onClose
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingQuestionScreenOld(
    testTitle: String = "Test 14",
    onClose: () -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Writing prompt", "Your response")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        testTitle,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Create, // Placeholder for Crown
                            contentDescription = "Premium",
                            tint = Color(0xFFFFD700)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = "Flag",
                            tint = Color(0xFFEF9A9A) // Light Red
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                     // Default indicator is fine
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        icon = {
                            if (index == 0) Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null, modifier = Modifier.size(20.dp)) // Placeholder icon
                            else Icon(Icons.Default.Create, contentDescription = null, modifier = Modifier.size(20.dp))
                        }
                    )
                }
            }
            
            if (selectedTabIndex == 0) {
                WritingPromptContent()
            } else {
                WritingResponseContent()
            }
        }
    }
}

@Composable
fun WritingPromptContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Question 1 of 5",
            color = Color(0xFF5C6BC0),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(24.dp)
                    .background(Color(0xFF5C6BC0))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "child / cry",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Image Placeholder
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Using launcher background as placeholder
            contentDescription = "Question Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Swipe right to write your response >>", // Mimicking the UI hint
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun WritingResponseContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Write your response here:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                disabledContainerColor = Color(0xFFF5F5F5),
            ),
            placeholder = { Text("Enter your sentence...") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WritingQuestionScreenPreview() {
    WritingQuestionScreen()
}
