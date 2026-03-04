package com.example.toeic_app.presentation.writing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toeic_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritingSelectionScreen(
    onNavigateToTestIntro: (Int, Int) -> Unit = { _, _ -> },
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "WRITING",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        // Crown icon placeholder make it yellow or gold if possible, but basic Icon for now
                        // In real app, use R.drawable.ic_crown or similar
                        Icon(
                            imageVector = Icons.Default.Create, // Placeholder for Crown
                            contentDescription = "Premium",
                            tint = Color(0xFFFFD700)
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    WritingCategoryItem(
                        title = "LV1: WRITING Q1-5: Write a sentence based on a picture",
                        completedCount = 1,
                        totalCount = 15,
                        onTestClick = { testId -> onNavigateToTestIntro(testId, 1) }
                    )
                }
                item {
                    WritingCategoryItem(
                        title = "LV2: WRITING Q6-7: Respond to a written request",
                        completedCount = 0,
                        totalCount = 15,
                        onTestClick = { testId -> onNavigateToTestIntro(testId, 6) }
                    )
                }
                item {
                    WritingCategoryItem(
                        title = "LV3: WRITING Q8: Write an opinion essay",
                        completedCount = 0,
                        totalCount = 15,
                        onTestClick = { testId -> onNavigateToTestIntro(testId, 8) }
                    )
                }
            }
        }
    }
}

@Composable
fun WritingCategoryItem(
    title: String,
    completedCount: Int,
    totalCount: Int,
    onTestClick: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Left Icon (Hand writing)
        Box(
            modifier = Modifier
                .size(60.dp)
                .padding(top = 8.dp)
        ) {
             // Placeholder for the hand-writing image
             Icon(
                 imageVector = Icons.Default.Create,
                 contentDescription = null,
                 modifier = Modifier.fillMaxSize(),
                 tint = Color(0xFFF48FB1) // Light pinkish
             )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface, // Adapts to theme
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Dropdown/Button Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(24.dp))
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$completedCount / $totalCount TESTS",
                        color = Color(0xFF9FA8DA),
                        fontWeight = FontWeight.Bold
                    )
                    
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF5C6BC0)), // Blueish
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    // List of tests (Mock data)
                    for (i in 1..5) {
                        Text(
                            text = "Test ${i}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTestClick(i) }
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WritingSelectionScreenPreview() {
    WritingSelectionScreen()
}
