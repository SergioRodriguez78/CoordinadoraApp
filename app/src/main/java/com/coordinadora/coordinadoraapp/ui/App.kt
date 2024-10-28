package com.coordinadora.coordinadoraapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coordinadora.coordinadoraapp.atomicDesign.theme.CoordinadoraAppTheme
import com.coordinadora.coordinadoraapp.navigation.routes.CoordinadoraRoutes
import com.coordinadora.coordinadoraapp.onboarding.login.ui.LoginScreen
import com.coordinadora.coordinadoraapp.onboarding.splash.ui.SplashScreen

val LocalNavController =
    compositionLocalOf<NavHostController> { error("NavController not provided") }

@Composable
fun App() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        CoordinadoraAppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = CoordinadoraRoutes.Splash,
                ) {
                    composable<CoordinadoraRoutes.Splash> { SplashScreen() }
                    composable<CoordinadoraRoutes.Login> { LoginScreen() }
                }
            }
        }
    }
}
