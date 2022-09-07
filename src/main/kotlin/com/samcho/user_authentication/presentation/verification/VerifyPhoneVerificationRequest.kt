package com.samcho.user_authentication.presentation.verification

data class VerifyPhoneVerificationRequest(
    val countryCode : String,
    val phoneNumber: String,
    val verificationCode: String,
)
