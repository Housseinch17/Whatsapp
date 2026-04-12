package com.example.whatsapp.core.presentation.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.whatsapp.R

private val robotoRegular = Font(R.font.roboto_regular, FontWeight.Normal)
private val robotoSemiBold = Font(R.font.roboto_semibold, FontWeight.SemiBold)
private val robotoBold = Font(R.font.roboto_bold, FontWeight.Bold)

val robotoFamily = FontFamily(
    fonts = listOf(
        robotoRegular,
        robotoSemiBold,
        robotoBold
    )
)
