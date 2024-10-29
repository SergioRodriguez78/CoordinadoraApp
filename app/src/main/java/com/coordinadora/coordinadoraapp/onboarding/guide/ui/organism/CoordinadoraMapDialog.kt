package com.coordinadora.coordinadoraapp.onboarding.guide.ui.organism

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.coordinadora.coordinadoraapp.atomicDesign.atoms.CoordinadoraButton

@Composable
fun CoordinadoraMapDialog(
    onDismiss: () -> Unit,
    bitmaps: List<Bitmap>
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(34.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PdfViewerPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(400.dp),
                bitmaps = bitmaps
            )
            CoordinadoraButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Close")
            }
        }
    }
}
