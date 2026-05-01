package com.example.whatsapp.feature.chat.presentation.ui.screens.chats

import com.example.whatsapp.core.data.Constants
import com.example.whatsapp.core.data.model.User

data class ChatUiState(
    val isEditSelected: Boolean = false,
    val users: List<User> = Constants.users,
    val selectedUsers: List<User> = emptyList(),
){
    val canArchiveOrReadOrDelete: Boolean = selectedUsers.isNotEmpty()
}
