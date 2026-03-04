package com.example.toeic_app.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toeic_app.presentation.auth.viewModel.AuthViewModel
import com.example.toeic_app.presentation.auth.viewModel.AuthState
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.toeic_app.presentation.auth.components.AuthButton
import com.example.toeic_app.presentation.auth.components.AuthTextField
import com.example.toeic_app.presentation.auth.components.SocialLoginButton

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var account by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                Toast.makeText(context, "Sign In Success", Toast.LENGTH_SHORT).show()
                onSignInSuccess()
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo (Placeholder)
        // In real app, use Image(painter = painterResource(...))
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
             // Hexagonal shape simulation or just text for now
             Text(
                 text = "TOEIC®\nTest Pro",
                 color = MaterialTheme.colorScheme.primary,
                 fontSize = 24.sp,
                 fontWeight = FontWeight.Bold,
                 textAlign = TextAlign.Center
             )
        }
        
        Spacer(modifier = Modifier.height(40.dp))

        // Form
        AuthTextField(
            value = account,
            onValueChange = { account = it },
            placeholder = "Enter your email"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Enter your password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Text(
                text = "Forgot password",
                color = Color(0xFF64B5F6), // Light blue link color
                fontSize = 14.sp,
                modifier = Modifier.clickable { /* TODO */ }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (authState is AuthState.Loading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            AuthButton(
                text = "Sign In",
                onClick = { viewModel.signIn(account, password) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Or separator
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = " - Or - ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SocialLoginButton(
            text = "Sign in with Google",
            onClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Footer
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you have any account? ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
            Text(
                text = "Sign up",
                color = Color(0xFF64B5F6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onNavigateToSignUp()
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
