package com.samcho.user_authentication.domain.verification

import com.samcho.user_authentication.data.verification.MemoryVerificationRepository
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class VerificationServiceTest {

    private val verificationRepository = MemoryVerificationRepository()
    private val verificationService: VerificationService = VerificationService(verificationRepository)

    @AfterEach
    fun afterEach() {
        verificationRepository.clearDB()
    }

    @Test
    fun createVerification() {
        val verification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("000000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        val savedVerification = verificationService.createVerification(verification)

        val foundVerification = verificationRepository.findById(verification.verificationChannel)

        assertEquals(foundVerification!!.verificationChannel, savedVerification.verificationChannel)
    }

    @Test
    fun verifyVerificationValid() {
        val verification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("000000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        verificationService.createVerification(verification)


        assertDoesNotThrow {
            verificationService.verifyVerification(verification)
        }

        assertEquals(null, verificationRepository.findById(verification.verificationChannel))
    }

    @Test
    fun verifyVerificationWrongCode() {
        val verification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("000000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        verificationService.createVerification(verification)

        val invalidVerification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("010000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        assertThrows(VerificationFailureException::class.java) {
            verificationService.verifyVerification(invalidVerification)
        }
    }

    @Test
    fun verifyVerificationExpired() {
        val verification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("000000"),
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.DAYS))
        )

        verificationService.createVerification(verification)

        val invalidVerification = Verification(
            verificationChannel = PhoneNumber("821012341234"),
            verificationCode = VerificationCode("010000"),
            expiration = Timestamp.from(Instant.now().minus(10, ChronoUnit.DAYS))
        )

        assertThrows(VerificationFailureException::class.java) {
            verificationService.verifyVerification(invalidVerification)
        }
    }

}