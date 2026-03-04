package com.example.toeic_app.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toeic_app.domain.usecase.theme.GetThemeUseCase
import com.example.toeic_app.domain.usecase.theme.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : ViewModel() {

    // Lấy Theme từ UseCase và chuyển thành StateFlow cho Giao diện đọc
    val isLightMode = getThemeUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled = _notificationsEnabled.asStateFlow()

    private val _notificationTime = MutableStateFlow("20:00")
    val notificationTime = _notificationTime.asStateFlow()

    private val _language = MutableStateFlow("English")
    val language = _language.asStateFlow()

    fun toggleLightMode(enabled: Boolean) {
        viewModelScope.launch {
            setThemeUseCase(enabled) // Gọi UseCase xuống kho để lưu thay đổi
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
    }
}
