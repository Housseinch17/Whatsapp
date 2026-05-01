package com.example.whatsapp.feature.chat.presentation.ui.screens.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.core.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<ChatEvents>()
    val events = _events.receiveAsFlow()

    fun onActions(chatActions: ChatActions) {
        when (chatActions) {
            is ChatActions.UpdateEditSelection -> updateEditSelection(isSelected = chatActions.isSelected)
            is ChatActions.OnArchiveClick -> onArchiveClick(user = chatActions.user)
            is ChatActions.OnMoreClick -> onMoreClick(user = chatActions.user)
            is ChatActions.OnSelectUser -> onSelectUser(user = chatActions.user)
            ChatActions.OpenNewChat -> openNewChat()
            ChatActions.OnArchiveAll -> onArchiveAll()
            ChatActions.OnDelete -> onDelete()
            ChatActions.OnReadAll -> onReadAll()
        }
    }

    private fun updateEditSelection(isSelected: Boolean) {
        _state.update { newState ->
            newState.copy(
                isEditSelected = isSelected
            )
        }
    }

    private fun onArchiveClick(user: User) {

    }

    private fun onMoreClick(user: User) {

    }

    private fun onSelectUser(user: User) {
        _state.update { newState ->
            val isSelected = newState.selectedUsers.any { it.uid == user.uid }
            val newSelectedUsers = if (isSelected) {
                newState.selectedUsers.filterNot { it.uid == user.uid }
            } else {
                newState.selectedUsers + user
            }

            newState.copy(
                selectedUsers = newSelectedUsers
            )
        }
    }

    private fun onArchiveAll(){

    }

    private fun onReadAll(){

    }

    private fun onDelete(){

    }

    private fun openNewChat(){
        viewModelScope.launch {
            _events.send(ChatEvents.NavigateToNewChat)
        }
    }
}