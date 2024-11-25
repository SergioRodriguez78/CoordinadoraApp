package com.coordinadora.coordinadoraapp.atomicDesign.atoms

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.coordinadora.coordinadoraapp.atomicDesign.theme.LightBlue

@Composable
fun CoordinadoraButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(19.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = LightBlue
    ),
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier.width(300.dp),
        shape = shape,
        enabled = enabled,
        content = content,
        onClick = onClick,
        colors =  colors,
    )
}