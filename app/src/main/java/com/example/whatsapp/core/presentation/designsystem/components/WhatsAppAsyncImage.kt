package com.example.whatsapp.core.presentation.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.whatsapp.R

@Composable
fun WhatsAppAsyncImage(
    modifier: Modifier = Modifier.size(52.dp),
    imageUrl: Any?,
    contentDescription: String,
    errorDrawable: Int = R.drawable.profile,
) {
    AsyncImage(
        modifier = modifier.clip(CircleShape),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .error(errorDrawable)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds
    )
}