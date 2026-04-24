package com.example.whatsapp.feature.authentication

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsapp.app.MainActivity
import com.example.whatsapp.core.data.model.AuthState
import com.example.whatsapp.core.data.model.Country
import com.example.whatsapp.core.domain.FirebaseAuthentication
import com.example.whatsapp.core.presentation.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val firebaseAuthentication: FirebaseAuthentication
) : ViewModel() {
    private val _state = MutableStateFlow(AuthenticationUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<AuthenticationEvents>()
    val events = _events.receiveAsFlow()

    init {
        initialSetup()
    }

    private fun initialSetup() {
        viewModelScope.launch {
            firebaseAuthentication.authStateFlow.collect { result ->
                _state.update { newState ->
                    newState.copy(
                        authState = result
                    )
                }
                if (result is AuthState.Success) {
                    _events.send(AuthenticationEvents.NavigateToChats)
                }
                if (result is AuthState.Error) {
                    showToast(message = result.message)
                }
            }
        }
    }

    fun onActions(action: AuthenticationActions) {
        when (action) {
            is AuthenticationActions.UpdateCountry -> updateCountry(country = action.country)
            is AuthenticationActions.OnDone -> onDone(activity = action.activity)
            is AuthenticationActions.UpdateIsExpanded -> updateIsExpanded(isExpanded = action.isExpanded)
            is AuthenticationActions.UpdatePhoneNumber -> updatePhoneNumber(phoneNumber = action.phoneNumber)
            is AuthenticationActions.UpdateVerificationCode -> updateVerificationCode(
                verificationCode = action.verificationCode
            )

            AuthenticationActions.VerifyOtp -> verifyOtp()
            AuthenticationActions.DismissOtpVerification -> dismissOtpVerification()
        }
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        if (phoneNumber.isDigitsOnly()) {
            _state.update { newState ->
                newState.copy(
                    phoneNumber = phoneNumber
                )
            }
        }
    }

    private fun updateIsExpanded(isExpanded: Boolean) {
        _state.update { newState ->
            newState.copy(
                isExpanded = isExpanded
            )
        }
    }

    private fun updateCountry(country: Country) {
        _state.update { newState ->
            newState.copy(
                selectedCountry = country
            )
        }
    }

    private fun onDone(activity: MainActivity) {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(
                    isLoading = true
                )
            }
            val phoneNumber = _state.value.phoneNumber
            val code = _state.value.selectedCountry.code
            val phoneWithExtension = code + phoneNumber
            firebaseAuthentication.sendOtp(phoneNumber = phoneWithExtension, activity = activity)
            delay(1.seconds)
            _state.update { newState ->
                newState.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun updateVerificationCode(verificationCode: String) {
        if (verificationCode.isDigitsOnly()) {
            _state.update { newState ->
                newState.copy(
                    verificationCode = verificationCode
                )
            }
        }
    }

    private fun verifyOtp() {
        viewModelScope.launch {
            _state.update { newState ->
                newState.copy(
                    isVerifying = true
                )
            }
            val verificationCode = _state.value.verificationCode
            val verify = firebaseAuthentication.verifyOtp(code = verificationCode)
            _state.update { newState ->
                newState.copy(
                    description = when (verify) {
                        is AuthState.Error -> UiText.DynamicString(verify.message)
                        else -> UiText.DynamicString("")
                    },
                    isVerifying = false
                )
            }
            if (verify is AuthState.Success) {
                _state.update { newState ->
                    newState.copy(
                        authState = AuthState.Idle
                    )
                }
                _events.send(AuthenticationEvents.NavigateToChats)
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch {
            _events.send(AuthenticationEvents.ShowToast(message = message))
        }
    }

    private fun dismissOtpVerification() {
        _state.update { newState ->
            newState.copy(
                authState = AuthState.Idle,
                verificationCode = "",
            )
        }
    }
}