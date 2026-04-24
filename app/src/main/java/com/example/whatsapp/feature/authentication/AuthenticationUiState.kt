package com.example.whatsapp.feature.authentication

import com.example.whatsapp.R
import com.example.whatsapp.core.data.Constants
import com.example.whatsapp.core.data.model.AuthState
import com.example.whatsapp.core.data.model.Country
import com.example.whatsapp.core.presentation.ui.UiText

data class AuthenticationUiState(
    val selectedCountry: Country = Constants.country.first(),
    val countryList: List<Country> = Constants.country,
    val isExpanded: Boolean = false,
    val phoneNumber: String = "",
    val authState: AuthState = AuthState.Idle,
    val verificationCode: String = "",
    val description: UiText = UiText.StringResource(R.string.verification_code_description),
    val isLoading: Boolean = false,
    val isVerifying: Boolean = false
) {
    val isDoneEnabled = phoneNumber.isNotBlank() && !isLoading
    val showVerification = authState is AuthState.CodeSent
    val isVerifiedEnabled = !isVerifying && verificationCode.length == 6

}
