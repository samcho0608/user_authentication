package com.samcho.user_authentication.presentation.user

data class SignUpRequest (
    private val nickname : String,
    private val email : String,
    private val password : String,
    private val name : String
)