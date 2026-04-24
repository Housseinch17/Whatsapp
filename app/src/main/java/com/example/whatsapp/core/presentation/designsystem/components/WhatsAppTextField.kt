package com.example.whatsapp.core.presentation.designsystem.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.whatsapp.core.presentation.designsystem.theme.gray3

@Composable
fun WhatsAppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
    ),
    placeHolderText: String,
    enabled: Boolean = true,
    showIndicator: Boolean = false,
){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        placeholder = {
            Text(
                text = placeHolderText,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = LocalContentColor.current
                )
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedPlaceholderColor = MaterialTheme.colorScheme.gray3,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.gray3,
            cursorColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedBorderColor = if(showIndicator) MaterialTheme.colorScheme.gray3 else Color.Transparent,
            unfocusedBorderColor = if(showIndicator) MaterialTheme.colorScheme.gray3 else Color.Transparent,
            disabledBorderColor = if(showIndicator) MaterialTheme.colorScheme.gray3 else Color.Transparent,
        )
    )
}