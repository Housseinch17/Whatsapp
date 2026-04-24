package com.example.whatsapp.core.domain

import com.example.whatsapp.app.MainActivity
import com.example.whatsapp.core.data.model.AuthState
import kotlinx.coroutines.flow.SharedFlow

interface FirebaseAuthentication {
    val authStateFlow: SharedFlow<AuthState>
    suspend fun sendOtp(phoneNumber: String,activity: MainActivity)
    suspend fun resendOtp(phoneNumber: String, activity: MainActivity)
    suspend fun verifyOtp(code: String): AuthState
}