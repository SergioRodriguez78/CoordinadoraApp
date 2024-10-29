package com.coordinadora.coordinadoraapp.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coordinadora.coordinadoraapp.R
import com.coordinadora.coordinadoraapp.atomicDesign.atoms.CoordinadoraButton
import com.coordinadora.coordinadoraapp.navigation.routes.CoordinadoraRoutes
import com.coordinadora.coordinadoraapp.core.ScreenState
import com.coordinadora.coordinadoraapp.onboarding.login.viewmodel.LoginViewModel
import com.coordinadora.coordinadoraapp.ui.LocalNavController

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    viewModel: LoginViewModel
) {
    val navigator = LocalNavController.current

    val userName by viewModel.username.collectAsStateWithLifecycle()
    val pwd by viewModel.password.collectAsStateWithLifecycle()
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state == ScreenState.Success) {
            navigator.navigate(CoordinadoraRoutes.Home)
        }
    }


    Column(
        Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineSmall
        )

        Image(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(200.dp),
            painter = painterResource(R.drawable.coordinadora_login_logo),
            contentDescription = null
        )

        TextField(
            value = userName,
            onValueChange = { viewModel.updateUsername(it) },
            label = {
                Text(stringResource(R.string.login_username_label))
            }
        )

        TextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = pwd,
            onValueChange = { viewModel.updatePassword(it) },
            label = {
                Text(stringResource(R.string.login_pwd_label))
            }
        )

        CoordinadoraButton(
            onClick = {
                viewModel.login(username = userName, password = pwd)
            }
        ) {
            Text(stringResource(R.string.login_login_button))
        }
    }

}
