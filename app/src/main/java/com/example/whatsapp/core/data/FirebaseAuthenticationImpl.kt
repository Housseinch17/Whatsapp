package com.example.whatsapp.core.data

import com.example.whatsapp.app.MainActivity
import com.example.whatsapp.core.data.model.AuthState
import com.example.whatsapp.core.domain.FirebaseAuthentication
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthenticationImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val scope: CoroutineScope
) : FirebaseAuthentication {
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val _authStateFlow = MutableSharedFlow<AuthState>()
    override val authStateFlow: SharedFlow<AuthState> = _authStateFlow.asSharedFlow()

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Timber.tag("MyTag").d("onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Timber.tag("MyTag").d("onVerificationFailed: ${e.localizedMessage}")
            scope.launch {
                _authStateFlow.emit(
                    AuthState.Error(
                        message = e.localizedMessage ?: "Phone number format is not valid"
                    )
                )
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Timber.tag("MyTag").d("onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token

            scope.launch {
                _authStateFlow.emit(AuthState.CodeSent)
            }
        }
    }

    override suspend fun sendOtp(phoneNumber: String, activity: MainActivity) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks)// OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //since billing not enabled we do not need the resendOtp since the verification
    //code is already set manually in firebase and no sms is sent/delivered
    override suspend fun resendOtp(phoneNumber: String, activity: MainActivity) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyOtp(code: String): AuthState {
        storedVerificationId?.let {
            val credential = PhoneAuthProvider.getCredential(it, code)
            return verifyPhoneAuthCredential(credential = credential)
        }
        return AuthState.Idle
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                scope.launch {
                    if (task.isSuccessful) {
                        val user = task.result?.user
                        Timber.tag("MyTag").d("signInWithCredential:success: user: $user")
                        _authStateFlow.emit(AuthState.Success)
                    } else {
                        Timber.tag("MyTag").d("signInWithCredential:failure ${task.exception}")
                        _authStateFlow.emit(
                            AuthState.Error(
                                task.exception?.localizedMessage ?: "Sign in failed"
                            )
                        )
                    }
                }
            }
    }

    private suspend fun verifyPhoneAuthCredential(credential: PhoneAuthCredential): AuthState {
        return suspendCoroutine { continuation ->
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    scope.launch {
                        if (task.isSuccessful) {
                            val user = task.result?.user
                            Timber.tag("MyTag").d("verifyPhoneAuthCredential:success: user: $user")
                            continuation.resume(AuthState.Success)
                        } else {
                            Timber.tag("MyTag")
                                .d("verifyPhoneAuthCredential:failure ${task.exception}")
                            continuation.resume(
                                AuthState.Error(
                                    task.exception?.localizedMessage ?: "Sign in failed"
                                )
                            )
                        }
                    }
                }
        }
    }
}