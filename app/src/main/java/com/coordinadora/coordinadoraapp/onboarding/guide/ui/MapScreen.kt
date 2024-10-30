package com.coordinadora.coordinadoraapp.onboarding.guide.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coordinadora.coordinadoraapp.R
import com.coordinadora.coordinadoraapp.atomicDesign.atoms.CoordinadoraButton
import com.coordinadora.coordinadoraapp.onboarding.guide.viewmodel.GuideViewModel
import com.coordinadora.coordinadoraapp.onboarding.guide.ui.organism.CoordinadoraMapDialog
import com.coordinadora.coordinadoraapp.ui.LocalNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    viewModel: GuideViewModel,
    innerPadding: PaddingValues
) {

    val navigator = LocalNavController.current

    val locations by viewModel.locations.collectAsStateWithLifecycle()
    val bitmaps by viewModel.bitmaps.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        locations.firstOrNull()?.let { firstLocation ->
            position = CameraPosition.fromLatLngZoom(
                LatLng(firstLocation.latitude, firstLocation.longitude),
                10f
            )
        }
    }

    var openDialog by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            locations.forEach { point ->
                val position = LatLng(point.latitude, point.longitude)
                val markerState = rememberMarkerState(position = position)
                Marker(
                    state = markerState,
                    onClick = {
                        openDialog = true
                        false
                    }
                )
            }
        }

        CoordinadoraButton(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(32.dp),
            onClick = {
                navigator.popBackStack()
            }
        ) {
            Text(stringResource(R.string.common_back))
        }

        if (openDialog) {
            CoordinadoraMapDialog(
                onDismiss = { openDialog = false },
                bitmaps = bitmaps
            )
        }
    }
}
