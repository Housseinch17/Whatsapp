package com.example.whatsapp.feature.authentication

sealed interface AuthenticationEvents {
    data object NavigateToChats: AuthenticationEvents
    data class ShowToast(val message: String): AuthenticationEvents
}