package com.samcho.user_authentication.domain.verification.verification_code

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class VerificationCodeTest {

    @Test
    fun testNonNumericVerificationCode() {
        val code = "ABCDEFG"

        assertThrows(InvalidVerificationCodeException::class.java) {
            VerificationCode(code)
        }
    }

    @Test
    fun testVerificationCodeLength() {

        for (i in 0 .. 10) {
            val codeChars = mutableListOf<String>()

            for (j in 0 until i) {
                codeChars.add("0")
            }
            val code = codeChars.joinToString("")

            if(code.length == 6) {
                assertEquals(code, VerificationCode(code).code)
            } else {
                assertThrows(InvalidVerificationCodeException::class.java) {
                    VerificationCode(code)
                }
            }
        }


    }





}