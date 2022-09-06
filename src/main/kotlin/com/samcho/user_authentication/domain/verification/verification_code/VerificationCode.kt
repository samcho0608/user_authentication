package com.samcho.user_authentication.domain.verification.verification_code

data class VerificationCode(
    val code : String,
) {
    init {
        if(!Regex("^\\d{6}$").matches(code)) {
            throw InvalidVerificationCodeException()
        }
    }
}
