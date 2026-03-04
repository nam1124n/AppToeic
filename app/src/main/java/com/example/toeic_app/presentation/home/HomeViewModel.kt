package com.example.toeic_app.presentation.home

import androidx.lifecycle.ViewModel
import com.example.toeic_app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0) // 0: Progress, 1: Activity
    val selectedTab = _selectedTab.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin = _isAdmin.asStateFlow()

    init {
        checkAdminStatus()
    }

    private fun checkAdminStatus() {
        val user = authRepository.getCurrentUser()
        _isAdmin.value = user?.email == "admin@gmail.com"
    }

    fun onTabSelected(index: Int) {
        _selectedTab.value = index
    }
}
