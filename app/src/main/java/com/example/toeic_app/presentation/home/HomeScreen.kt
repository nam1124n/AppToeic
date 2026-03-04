package com.example.toeic_app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toeic_app.presentation.home.components.StreakHeader

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToWriting: () -> Unit = {},
    onNavigateToAdmin: () -> Unit = {},
    onOpenDrawer: () -> Unit = {}
) {
    val isAdmin by viewModel.isAdmin.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        HomeHeader(onOpenDrawer = onOpenDrawer)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        StreakHeader()
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Scrollable content
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                GoalBanner()
            }
            
            if (isAdmin) {
                item {
                    Button(
                        onClick = onNavigateToAdmin,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error) // Red button for Admin
                    ) {
                        Text("Admin Panel - Quản lý Đề Thi Writing", color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
            
            item {
                SectionHeader(title = "Skill-focused practice")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Skills link with learning path (Total 9 parts - You level up when all 9 parts reach a higher level)",
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // Gray
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                SkillFocusedPracticeGrid()
            }
            
            item {
                SectionHeader(title = "Other skills you can practice")
                Spacer(modifier = Modifier.height(16.dp))
                OtherSkillsGrid(onNavigateToWriting = onNavigateToWriting)
            }
            
            item {
                 SectionHeader(title = "TOEIC Test Prep")
            }
        }
    }
}

@Composable
fun HomeHeader(
    onOpenDrawer: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .size(40.dp)
        ) {
            Icon(Icons.Rounded.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = { /* Premium */ },
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                    .size(40.dp)
            ) {
                // Crown icon placeholder tint
                Icon(Icons.Default.CalendarToday, contentDescription = "Premium", tint = Color(0xFFF59E0B)) 
            }
            IconButton(
                onClick = { /* Calendar */ },
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                    .size(40.dp)
            ) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Calendar", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun GoalBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFFD700), Color(0xFFFF8C00)) // Yellow to Orange
                )
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday, // Cup placeholder
                contentDescription = "Cup",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Set your learning goal",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "to unlock learning path & skill practice",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.Default.CalendarToday, // Arrow placeholder
                contentDescription = "Arrow",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(16.dp)
                .background(Color(0xFF00BFFF), RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground, // Dark text
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun SkillFocusedPracticeGrid() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SkillCard(
            title = "Listening",
            icon = Icons.Default.Headphones,
            gradientColors = listOf(Color(0xFFFF7F50), Color(0xFFFF1493)), // Coral to Pink
            modifier = Modifier.weight(1f),
            onClick = {}
        )
        SkillCard(
            title = "Reading",
            icon = Icons.Default.MenuBook,
            gradientColors = listOf(Color(0xFFFFE4B5), Color(0xFFFF4500)), // LightOrange to RedOrange
            modifier = Modifier.weight(1f),
            onClick = {}
        )
    }
}

@Composable
fun OtherSkillsGrid(
    onNavigateToWriting: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkillCard(
                title = "Speaking",
                icon = Icons.Default.RecordVoiceOver, // Speaker/Chat
                gradientColors = listOf(Color(0xFF00BFFF), Color(0xFF1E90FF)), // Light Blue
                modifier = Modifier.weight(1f),
                onClick = {}
            )
            SkillCard(
                title = "Writing",
                icon = Icons.Default.Create, // Pen/Write
                gradientColors = listOf(Color(0xFF9370DB), Color(0xFF8A2BE2)), // Purple
                modifier = Modifier.weight(1f),
                onClick = onNavigateToWriting
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkillCard(
                title = "Vocabulary",
                icon = Icons.Default.MenuBook, // Book/ABC
                gradientColors = listOf(Color(0xFF90EE90), Color(0xFF32CD32)), // Green
                modifier = Modifier.weight(1f),
                onClick = {}
            )
            SkillCard(
                title = "Grammar",
                icon = Icons.Default.MenuBook, // Blocks/S+V
                gradientColors = listOf(Color(0xFFBA55D3), Color(0xFF9400D3)), // Violet
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }
    }
}

@Composable
fun SkillCard(
    title: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() }
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon with gradient logic requires drawing specially or using a tinted resource.
            // For simplicity, we use the gradient for the icon tint if possible, or just a solid color from the list.
            // Detailed gradient icon requires `ShaderBrush`.
            
            // Icon Placeholder
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = gradientColors.first(), // Use first color of gradient for now
                modifier = Modifier.size(48.dp) 
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface, // Dark text
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
