package com.example.whatsapp.app.navigation

import com.example.whatsapp.R
import com.example.whatsapp.core.presentation.ui.UiText
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationScreens {
    abstract val name: UiText
    abstract val icon: Int

    @Serializable
    data object AuthenticationScreen : NavigationScreens() {
        override val name = UiText.DynamicString("")
        override val icon = R.drawable.chat
    }

    @Serializable
    data object ChatScreen : NavigationScreens() {
        override val name = UiText.StringResource(R.string.chats)
        override val icon = R.drawable.chat
    }

    @Serializable
    data object StatusScreen : NavigationScreens() {
        override val name = UiText.StringResource(R.string.status)
        override val icon = R.drawable.status
    }

    @Serializable
    data object CallScreen : NavigationScreens() {
        override val name = UiText.StringResource(R.string.call)
        override val icon = R.drawable.call
    }

    @Serializable
    data object CameraScreen : NavigationScreens() {
        override val name = UiText.StringResource(R.string.camera)
        override val icon = R.drawable.camera
    }

    @Serializable
    data object SettingsScreen : NavigationScreens() {
        override val name = UiText.StringResource(R.string.settings)
        override val icon = R.drawable.settings
    }
}