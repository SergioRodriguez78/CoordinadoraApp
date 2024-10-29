package com.coordinadora.coordinadoraapp.onboarding.home.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PagerStatus(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                onPreviousClick()
            },
            enabled = currentPage > 0
        ) {
            Text("Previous")
        }

        Text(
            text = if (totalPages > 0) "Page ${currentPage + 1} of $totalPages" else "Page 0 of 0",
            modifier = Modifier
                .padding(16.dp)
        )

        Button(
            onClick = {
                onNextClick()
            },
            enabled = currentPage < totalPages - 1
        ) {
            Text("Next")
        }
    }
}
