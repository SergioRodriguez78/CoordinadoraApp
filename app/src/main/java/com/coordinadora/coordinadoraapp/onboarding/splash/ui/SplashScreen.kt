package com.coordinadora.coordinadoraapp.onboarding.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.coordinadora.coordinadoraapp.R
import com.coordinadora.coordinadoraapp.atomicDesign.theme.DarkBlue
import com.coordinadora.coordinadoraapp.navigation.routes.CoordinadoraRoutes
import com.coordinadora.coordinadoraapp.ui.LocalNavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(CoordinadoraRoutes.Login)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp),
            painter = painterResource(R.drawable.coordinadora_logo_splash),
            contentDescription = null
        )
    }
}
