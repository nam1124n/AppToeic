package com.example.toeic_app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Statistic : Screen("statistic_screen")
    object Setting : Screen("setting_screen")
    object SignIn : Screen("signin_screen")
    object SignUp : Screen("signup_screen")
    object WritingSelection : Screen("writing_selection_screen")
    object WritingIntro : Screen("writing_intro_screen/{testId}/{questionType}")
    object WritingQuestion : Screen("writing_question_screen/{testId}/{questionType}")
    object Practice : Screen("practice_screen")
    object AdminWritingList : Screen("admin_writing_list_screen")
    object AdminWritingEdit : Screen("admin_writing_edit_screen?questionId={questionId}")
}
