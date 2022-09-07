package com.samcho.user_authentication.presentation.user

data class ResetPasswordRequest(
    val password: String,
    val newPassword: String,
)