package com.example.whatsapp.feature.chat.presentation.ui.screens.chats

sealed interface ChatEvents {
    data object NavigateToNewChat: ChatEvents
}