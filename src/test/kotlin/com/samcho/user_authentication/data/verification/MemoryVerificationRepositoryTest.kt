package com.samcho.user_authentication.data.verification

import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.verification.Verification
import com.samcho.user_authentication.domain.verification.VerificationRepository
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

internal class MemoryVerificationRepositoryTest {

    private val _verificationRepository = MemoryVerificationRepository()
    private val verificationRepository : VerificationRepository = _verificationRepository

    @AfterEach
    fun afterEach() {
        _verificationRepository.clearDB()
    }

    @Test
    fun save() {
        val verification = Verification(
            verificationCode = VerificationCode("000000"),
            verificationChannel = PhoneNumber("821012341234"),
            expiration = Timestamp.from(Instant.now().plus(20, ChronoUnit.DAYS)),
        )

        val savedVerification = verificationRepository.save(verification)

        assertEquals(verification, savedVerification)
    }

    @Test
    fun findById() {
        val verification = Verification(
            verificationCode = VerificationCode("000000"),
            verificationChannel = PhoneNumber("821012341234"),
            expiration = Timestamp.from(Instant.now().plus(20, ChronoUnit.DAYS)),
        )

        val savedVerification = verificationRepository.save(verification)

        val foundVerification = verificationRepository.findById(savedVerification.verificationChannel)

        assertEquals(verification, savedVerification)
        assertEquals(verification, foundVerification)
    }

    @Test
    fun findByIdNotFound() {
        val verificationChannel = PhoneNumber("821012341234")

        val foundVerification = verificationRepository.findById(verificationChannel)

        assertEquals(null, foundVerification)
    }

    @Test
    fun delete() {
        val verification = Verification(
            verificationCode = VerificationCode("000000"),
            verificationChannel = PhoneNumber("821012341234"),
            expiration = Timestamp.from(Instant.now().plus(20, ChronoUnit.DAYS)),
        )

        val savedVerification = verificationRepository.save(verification)

        verificationRepository.delete(savedVerification)

        assertEquals(null, verificationRepository.findById(verification.verificationChannel))
    }
}