package com.coordinadora.coordinadoraapp.onboarding.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coordinadora.coordinadoraapp.R
import com.coordinadora.coordinadoraapp.atomicDesign.atoms.CoordinadoraButton
import com.coordinadora.coordinadoraapp.atomicDesign.theme.DarkBlue
import com.coordinadora.coordinadoraapp.core.ScreenState
import com.coordinadora.coordinadoraapp.onboarding.home.ui.organism.PdfViewerPager
import com.coordinadora.coordinadoraapp.onboarding.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val bitmaps by viewModel.bitmaps.collectAsStateWithLifecycle()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {

                    },
                painter = painterResource(R.drawable.map_icon),
                contentDescription = null,
                tint = DarkBlue
            )

            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.logo_top_coordinadora),
                contentDescription = null
            )

            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        viewModel.getData()
                    },
                painter = painterResource(R.drawable.refresh),
                contentDescription = "Refresh",
                tint = DarkBlue
            )
        }

        CoordinadoraButton(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                viewModel.getData()
            }
        ) {
            Text(stringResource(R.string.home_button))
        }

        if (state == ScreenState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        } else {
            PdfViewerPager(bitmaps)
        }
    }
}
