package com.example.whatsapp.feature.auth.authentication

sealed interface AuthenticationEvents {
    data object NavigateToChats: AuthenticationEvents
    data class ShowToast(val message: String): AuthenticationEvents
}