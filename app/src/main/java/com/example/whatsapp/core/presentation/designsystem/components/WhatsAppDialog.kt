package com.example.whatsapp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp.R
import com.example.whatsapp.core.presentation.designsystem.theme.gray3
import com.example.whatsapp.core.presentation.designsystem.theme.robotoFamily

@Composable
fun WhatsAppDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    title: String,
    description: String,
    confirmButton: () -> Unit,
    isConfirmEnabled: Boolean,
    dismissButton: () -> Unit,
    isLoading: Boolean,
    content: @Composable () -> Unit = {},
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = LocalContentColor.current
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LocalContentColor.current,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (!isLoading) {
                    content()
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.gray3
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = confirmButton,
                enabled = isConfirmEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.3f
                    )
                )
            ) {
                Text(
                    text = stringResource(R.string.verify),
                    style = TextStyle(
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }
        },
        dismissButton = {
            Button(
                onClick = dismissButton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                )
            ) {
                Text(
                    text = stringResource(R.string.dismiss),
                    style = TextStyle(
                        fontFamily = robotoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.secondary,
        textContentColor = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.medium,
    )
}