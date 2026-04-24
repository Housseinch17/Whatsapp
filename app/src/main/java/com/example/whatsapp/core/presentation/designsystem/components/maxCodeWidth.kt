package com.example.whatsapp.core.presentation.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import com.example.whatsapp.core.data.model.Country

@Composable
fun maxCodeWidth(
    countryList: List<Country>,
    style: TextStyle = MaterialTheme.typography.labelMedium
): Dp {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    val maxWidthPx = countryList.maxOf { country ->
        textMeasurer.measure(
            text = AnnotatedString(country.code),
            style = style
        ).size.width
    }

    return with(density) { maxWidthPx.toDp() }
}