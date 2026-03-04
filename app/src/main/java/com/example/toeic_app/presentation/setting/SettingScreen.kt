package com.example.toeic_app.presentation.setting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val isLightMode by viewModel.isLightMode.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val notificationTime by viewModel.notificationTime.collectAsState()
    val language by viewModel.language.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                 // Placeholders for top corners (Menu, Crown, Calendar) if needed, 
                 // but Setting screen in screenshot usually has them or just title.
                 // Assuming same header or simple title? 
                 // Screenshot 1 shows full header. Screenshot 2 shows popup.
                 // Let's just put the card in a LazyColumn with some padding.
            }
        },
        containerColor = MaterialTheme.colorScheme.background// Or generic background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                SettingsCard(
                    isLightMode = isLightMode,
                    notificationsEnabled = notificationsEnabled,
                    notificationTime = notificationTime,
                    language = language,
                    onLightModeChange = viewModel::toggleLightMode,
                    onNotificationChange = viewModel::toggleNotifications
                )
            }
        }
    }
}

@Composable
fun SettingsCard(
    isLightMode: Boolean,
    notificationsEnabled: Boolean,
    notificationTime: String,
    language: String,
    onLightModeChange: (Boolean) -> Unit,
    onNotificationChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SettingItemSwitch(
                icon = Icons.Default.LightMode,
                title = "Light mode",
                checked = isLightMode,
                onCheckedChange = onLightModeChange,
                iconColor = Color(0xFF6366F1)
            )
            Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
            SettingItemNotification(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                time = notificationTime,
                checked = notificationsEnabled,
                onCheckedChange = onNotificationChange,
                iconColor = Color(0xFF6366F1)
            )
            Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
            SettingItemDropdown(
                icon = Icons.Default.Language,
                title = "Your language",
                value = language,
                iconColor = Color(0xFF6366F1)
            )
        }
    }
}

@Composable
fun SettingItemSwitch(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF6366F1),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
fun SettingItemNotification(
    icon: ImageVector,
    title: String,
    time: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        
        // Time Pill
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Row(
                 modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                 verticalAlignment = Alignment.CenterVertically
            ) {
                 Text(time, fontSize = 14.sp)
                 // text can be a dropdown 
                 // Icon(Icons.Default.ArrowDropDown, ...)
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
             colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF22C55E), // Green for notifications
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}

@Composable
fun SettingItemDropdown(
    icon: ImageVector,
    title: String,
    value: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                 modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                 verticalAlignment = Alignment.CenterVertically
            ) {
                 Text(value, fontSize = 14.sp)
                 // Icon dropdown
            }
        }
    }
}
