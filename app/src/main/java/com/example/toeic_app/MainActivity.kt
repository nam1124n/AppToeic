package com.example.toeic_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.toeic_app.presentation.components.BottomNavigationBar
import com.example.toeic_app.presentation.components.DrawerContent
import com.example.toeic_app.presentation.navigation.NavGraph
import com.example.toeic_app.presentation.navigation.Screen
import com.example.toeic_app.ui.theme.Toeic_appTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.toeic_app.domain.usecase.theme.GetThemeUseCase
import javax.inject.Inject
import com.example.toeic_app.domain.usecase.GetCurrentUserUseCase
import com.google.firebase.auth.FirebaseAuth

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getThemeUseCase: GetThemeUseCase

    @Inject
    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isLightMode by getThemeUseCase().collectAsState(initial = true)
            Toeic_appTheme(darkTheme = !isLightMode) {
                val startDestination = if (getCurrentUserUseCase() != null) {
                    Screen.Home.route
                } else {
                    Screen.SignIn.route
                }
                MainScreen(startDestination = startDestination)
            }
        }
    }
}

@Composable
fun MainScreen(startDestination: String = Screen.SignIn.route) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DrawerContent(
                onSignOut = {
                    scope.launch {
                        auth.signOut()
                        drawerState.close()
                        navController.navigate(Screen.SignIn.route)
                        {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            bottomBar = {
                if (currentRoute != Screen.SignIn.route && currentRoute != Screen.SignUp.route) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { innerPadding ->
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                NavGraph(
                    navController = navController,
                    startDestination = startDestination,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        }
    }
}