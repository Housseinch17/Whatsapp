package com.example.whatsapp.feature.authentication

import com.example.whatsapp.app.MainActivity
import com.example.whatsapp.core.data.model.Country

sealed interface AuthenticationActions {
    data class UpdateCountry(val country: Country): AuthenticationActions
    data class OnDone(val activity: MainActivity): AuthenticationActions
    data class UpdateIsExpanded(val isExpanded: Boolean): AuthenticationActions
    data class UpdatePhoneNumber(val phoneNumber: String): AuthenticationActions
    data class UpdateVerificationCode(val verificationCode: String): AuthenticationActions
    data object VerifyOtp: AuthenticationActions
    data object DismissOtpVerification: AuthenticationActions
}