package com.example.whatsapp.feature.chat.presentation.ui.screens.chats

import com.example.whatsapp.core.data.model.User

sealed interface ChatActions {
    data class UpdateEditSelection(val isSelected: Boolean) : ChatActions
    data class OnMoreClick(val user: User) : ChatActions
    data class OnArchiveClick(val user: User) : ChatActions
    data class OnSelectUser(val user: User): ChatActions
    data object OpenNewChat: ChatActions
    data object OnArchiveAll: ChatActions
    data object OnReadAll: ChatActions
    data object OnDelete: ChatActions
}