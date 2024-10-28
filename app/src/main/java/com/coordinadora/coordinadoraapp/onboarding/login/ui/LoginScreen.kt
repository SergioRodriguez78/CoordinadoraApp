package com.coordinadora.coordinadoraapp.onboarding.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.coordinadora.coordinadoraapp.R
import com.coordinadora.coordinadoraapp.atomicDesign.atoms.CoordinadoraButton

@Composable
fun LoginScreen() {

    var userName by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.splash_title),
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
            onValueChange = { userName = it },
            label = {
                Text(stringResource(R.string.splash_username_label))
            }
        )

        TextField(
            modifier = Modifier.padding(vertical = 16.dp),
            value = pwd,
            onValueChange = { pwd = it },
            label = {
                Text(stringResource(R.string.splash_pwd_label))
            }
        )

        CoordinadoraButton(
            onClick = {

            }
        ) {
            Text(stringResource(R.string.splash_login_button))
        }
    }

}
