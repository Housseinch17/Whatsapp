package com.example.whatsapp.core.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.whatsapp.R

object WhatsappIcons {
    val AddCall: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.add_call)

    val Archive: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.archive)

    val ArrowRight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.arrow_right)

    val Call: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.call)

    val Camera: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.camera)

    val Cancel: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.cancel)

    val Chat: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.chat)

    val Delete: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.delete)

    val Dots: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.dots)

    val Edit: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.edit)

    val Read: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.read)

    val Settings: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.settings)

    val Status: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.status)

    val VoiceRecord: ImageVector
        @Composable
        get() = ImageVector.vectorResource(R.drawable.voice_record)


}