package com.samcho.user_authentication.domain.verification

import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class VerificationTest {
    @Test
    fun testExpired() {
        val passedExpiration = Timestamp.from(Instant.now().minus(10, ChronoUnit.DAYS))

        val verification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("000000"),
            expiration = passedExpiration
        )

        assertEquals(true, verification.isExpired())
    }

    @Test
    fun testNotExpired() {
        val notPassedExpiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))

        val verification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("000000"),
            expiration = notPassedExpiration
        )

        assertEquals(false, verification.isExpired())
    }

    @Test
    fun testPhoneNumberVerification() {
        val phoneNumber = PhoneNumber("821012341234")

        val verification = Verification(
            verificationChannel = phoneNumber,
            verificationCode = VerificationCode("000000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        assertEquals(phoneNumber.phoneNumber, verification.verificationChannel!!.destination())
    }

    @Test
    fun testEmailAddressVerification() {
        val emailAddress = EmailAddress("abc@example.com")

        val verification = Verification(
            verificationChannel = emailAddress,
            verificationCode = VerificationCode("000000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        assertEquals(emailAddress.emailAddress, verification.verificationChannel!!.destination())
    }
}