package com.samcho.user_authentication.presentation.verification

data class PhoneVerificationRequest(
    val countryCode: String,
    val phoneNumber : String,
)
