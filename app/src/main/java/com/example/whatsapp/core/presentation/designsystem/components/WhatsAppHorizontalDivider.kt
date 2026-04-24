package com.example.whatsapp.core.presentation.designsystem.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.whatsapp.core.presentation.designsystem.theme.gray3

@Composable
fun WhatsAppHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.gray3
) {
    HorizontalDivider(modifier = modifier, color = color)
}