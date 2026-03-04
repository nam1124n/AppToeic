package com.example.toeic_app.presentation.auth.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toeic_app.domain.model.User
import com.example.toeic_app.domain.usecase.SignInUseCase
import com.example.toeic_app.domain.usecase.SignOutUseCase
import com.example.toeic_app.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User?) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = signUpUseCase(email, password)
            result.onSuccess { user ->
                _authState.value = AuthState.Success(user)
            }.onFailure { e ->
                _authState.value = AuthState.Error(e.message ?: "Đăng ký thất bại")
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = signInUseCase(email, password)
            result.onSuccess { user ->
                _authState.value = AuthState.Success(user)
            }.onFailure { e ->
                _authState.value = AuthState.Error(e.message ?: "Đăng nhập thất bại")
            }
        }
    }

    fun signOut() {
        signOutUseCase()
        _authState.value = AuthState.Idle
    }
}