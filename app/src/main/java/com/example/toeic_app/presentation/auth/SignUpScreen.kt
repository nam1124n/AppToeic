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
fun SignUpScreen(

    onNavigateToSignIn: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                Toast.makeText(context, "Sign Up Success", Toast.LENGTH_SHORT).show()
                onNavigateToSignIn()
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
            .background(Color(0xFF1E2439))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Logo (Smaller or same?)
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
             Text(
                 text = "TOEIC®\nTest Pro",
                 color = MaterialTheme.colorScheme.onPrimary,
                 fontSize = 20.sp,
                 fontWeight = FontWeight.Bold,
                 textAlign = TextAlign.Center
             )
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        // Form
        AuthTextField(
            value = displayName,
            onValueChange = { displayName = it },
            placeholder = "Enter your display name"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        AuthTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter your email (Account)"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Enter your password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        AuthTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Enter your confirm password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (authState is AuthState.Loading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        } else {
            AuthButton(
                text = "Sign Up",
                onClick = {
                    if (password == confirmPassword) {
                        viewModel.signUp(email, password)
                    } else {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                text = "Do you already have an account? ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
            Text(
                text = "Sign In",
                color = Color(0xFF64B5F6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onNavigateToSignIn()
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
