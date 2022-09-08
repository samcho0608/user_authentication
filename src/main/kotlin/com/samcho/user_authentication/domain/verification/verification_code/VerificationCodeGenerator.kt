package com.samcho.user_authentication.domain.verification.verification_code

import java.security.SecureRandom

abstract class VerificationCodeGenerator {

    companion object {
        private val secureRandom = SecureRandom.getInstanceStrong()
        fun generateVerificationCode(): VerificationCode {
            val digits = mutableListOf<Int>()
            for (i in 0 until 6) {
                digits.add(secureRandom.nextInt(10))
            }
            return VerificationCode(digits.joinToString(""))

        }
    }
}