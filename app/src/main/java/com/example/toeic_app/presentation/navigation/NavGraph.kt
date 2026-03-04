package com.example.toeic_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.toeic_app.presentation.home.HomeScreen
import com.example.toeic_app.presentation.setting.SettingScreen
import com.example.toeic_app.presentation.statistic.StatisticScreen
import com.example.toeic_app.presentation.auth.SignInScreen
import com.example.toeic_app.presentation.auth.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onOpenDrawer: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToWriting = {
                    navController.navigate(Screen.WritingSelection.route)
                },
                onNavigateToAdmin = {
                    navController.navigate(Screen.AdminWritingList.route)
                },
                onOpenDrawer = onOpenDrawer
            )
        }
        composable(Screen.Statistic.route) {
            StatisticScreen()
        }
        composable(Screen.Setting.route) {
            SettingScreen()
        }
        composable(Screen.SignIn.route) {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToSignIn = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.WritingSelection.route) {
            com.example.toeic_app.presentation.writing.WritingSelectionScreen(
                onNavigateToTestIntro = { testId, questionType ->
                    navController.navigate("writing_intro_screen/$testId/$questionType") 
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.WritingIntro.route,
            arguments = listOf(
                navArgument("testId") { type = NavType.IntType; defaultValue = 1 },
                navArgument("questionType") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getInt("testId") ?: 1
            val questionType = backStackEntry.arguments?.getInt("questionType") ?: 1
            com.example.toeic_app.presentation.writing.WritingIntroScreen(
                testId = testId,
                onStartClick = {
                    navController.navigate("writing_question_screen/$testId/$questionType")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.WritingQuestion.route,
            arguments = listOf(
                navArgument("testId") { type = NavType.IntType; defaultValue = 1 },
                navArgument("questionType") { type = NavType.IntType; defaultValue = 1 }
            )
        ) { backStackEntry ->
            val testId = backStackEntry.arguments?.getInt("testId") ?: 1
            val questionType = backStackEntry.arguments?.getInt("questionType") ?: 1
            com.example.toeic_app.presentation.writing.WritingQuestionScreen(
                testId = testId,
                questionNumber = questionType,
                onClose = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Practice.route) {
            com.example.toeic_app.presentation.practice.PracticeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.AdminWritingList.route) {
            com.example.toeic_app.presentation.admin.AdminWritingListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { questionId ->
                    if (questionId != null) {
                        navController.navigate("admin_writing_edit_screen?questionId=$questionId")
                    } else {
                        navController.navigate("admin_writing_edit_screen")
                    }
                }
            )
        }
        composable(
            route = Screen.AdminWritingEdit.route,
            arguments = listOf(
                navArgument("questionId") {
                    type = NavType.StringType // Pass as string since it could be null
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val questionIdStr = backStackEntry.arguments?.getString("questionId")
            val questionId = questionIdStr?.toIntOrNull()
            com.example.toeic_app.presentation.admin.AdminWritingEditScreen(
                questionId = questionId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
