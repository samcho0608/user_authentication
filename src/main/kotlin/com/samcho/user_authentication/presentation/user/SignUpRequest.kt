package com.samcho.user_authentication.presentation.user

data class SignUpRequest (
    val nickname : String,
    val email : String,
    val password : String,
    val name : String
)