package com.example.toeic_app.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toeic_app.presentation.auth.SignInScreen
import com.example.toeic_app.ui.theme.PrimaryBlue

@Composable
fun DrawerContent(
    onSignOut: () -> Unit = { },
    // Add other callbacks as needed
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.width(300.dp) // Standard drawer width
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header: User Profile
            DrawerHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // Premium Banner
            PremiumBanner()

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))

            // Menu Items
            Column(modifier = Modifier.weight(1f)) {
                DrawerMenuItem(icon = Icons.Outlined.Person, label = "Profile", onClick = {})
                DrawerMenuItem(icon = Icons.Outlined.Support, label = "Feedback & Support", onClick = {})
                DrawerMenuItem(icon = Icons.Outlined.Share, label = "Share Application", onClick = {})
                DrawerMenuItem(icon = Icons.Outlined.Security, label = "Privacy Policy", onClick = {})
                DrawerMenuItem(icon = Icons.Outlined.Description, label = "Terms and Conditions", onClick = {})
                
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
                
                DrawerMenuItem(
                    icon = Icons.Outlined.Delete, 
                    label = "Delete account", 
                    onClick = {},
                    tint = Color.Red,
                    textColor = Color.Red
                )
                DrawerMenuItem(icon = Icons.Outlined.Logout, label = "Sign out", onClick = onSignOut)
            }
            
            // Footer
            Text(
                text = "App version 3.1.5(2025121814)",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun DrawerHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF5C6AC4)), // Example Avatar Color
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "N",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "N8-LEVANNAM",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            // Optional subtitle if needed
        }
        Spacer(modifier = Modifier.weight(1f))
        
        // Calendar/Date small icon button if needed from design
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFE0E7FF), RoundedCornerShape(8.dp))
        ) {
            Icon(Icons.Default.CalendarToday, contentDescription = "Calendar", tint = PrimaryBlue, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun PremiumBanner() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                 // Use a place holder or resource for the crown image
                 Icon(
                     imageVector = Icons.Default.EmojiEvents, // Crown substitute
                     contentDescription = "Pro",
                     tint = Color(0xFFFFD700), // Gold
                     modifier = Modifier.size(32.dp)
                 )
                 Spacer(modifier = Modifier.width(12.dp))
                 Column {
                     Text(
                         text = "Get Pro",
                         fontWeight = FontWeight.Bold,
                         fontSize = 14.sp,
                         color = MaterialTheme.colorScheme.primary
                     )
                     Text(
                         text = "Get Higher Score!",
                         fontWeight = FontWeight.Bold,
                         fontSize = 14.sp,
                         color = MaterialTheme.colorScheme.primary
                     )
                 }
            }
            
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(text = "UPGRADE", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = FontWeight.Normal
        )
    }
}
