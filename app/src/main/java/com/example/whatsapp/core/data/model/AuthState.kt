package com.example.whatsapp.core.data.model

sealed class AuthState {
    data object Idle: AuthState()
    data object CodeSent : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}