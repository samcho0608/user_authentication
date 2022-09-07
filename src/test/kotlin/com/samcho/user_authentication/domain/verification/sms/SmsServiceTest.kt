package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SmsServiceTest @Autowired constructor(
    val smsService: SmsService
) {

    @Test
    fun sendVerificationMessage() {
        try {
            val response = smsService.sendVerificationSms(
                toNumber = "01084273267",
                code = VerificationCode("000000")
            )

            assertEquals("200", response!!.statusCode)

        } catch (e : Exception) {

        }
    }

    @Test
    fun sendVerificationMessageInvalidNumber() {
        val invalidPhoneNumber = "A123049473@"

        assertThrows(SmsFailureException::class.java) {
            smsService.sendVerificationSms(
                toNumber = invalidPhoneNumber,
                code = VerificationCode("000000")
            )
        }
    }
}