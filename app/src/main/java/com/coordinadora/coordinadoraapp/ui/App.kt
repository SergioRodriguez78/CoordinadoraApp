package com.coordinadora.coordinadoraapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coordinadora.coordinadoraapp.atomicDesign.theme.CoordinadoraAppTheme
import com.coordinadora.coordinadoraapp.navigation.routes.CoordinadoraRoutes
import com.coordinadora.coordinadoraapp.onboarding.guide.ui.HomeScreen
import com.coordinadora.coordinadoraapp.onboarding.guide.ui.MapScreen
import com.coordinadora.coordinadoraapp.onboarding.guide.viewmodel.GuideViewModel
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

                val sharedViewModel: GuideViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = CoordinadoraRoutes.Splash,
                ) {
                    composable<CoordinadoraRoutes.Splash> {
                        SplashScreen()
                    }
                    composable<CoordinadoraRoutes.Login> {
                        LoginScreen(innerPadding = innerPadding, viewModel = hiltViewModel())
                    }
                    composable<CoordinadoraRoutes.Home> {
                        HomeScreen(viewModel = sharedViewModel, paddingValues = innerPadding)
                    }
                    composable<CoordinadoraRoutes.Map> {
                        MapScreen(sharedViewModel, innerPadding)
                    }
                }
            }
        }
    }
}
