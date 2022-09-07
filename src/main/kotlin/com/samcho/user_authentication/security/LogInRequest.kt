package com.samcho.user_authentication.security

data class LogInRequest(
    val username: String,
    val password: String,
)
