package com.samcho.user_authentication.domain.verification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationService @Autowired constructor(
    private val verificationRepository: VerificationRepository
){

    fun verifyVerification(verification : Verification) {
        val foundVerification = verificationRepository.findById(verification.verificationChannel)
            ?: throw VerificationFailureException("Verification not found")

        if(verification.verificationCode.code != foundVerification.verificationCode.code) {
            throw VerificationFailureException("Verification code mismatch")
        }

        if(foundVerification.isExpired()) {
            throw VerificationFailureException("Verification expired")
        }

        verificationRepository.delete(foundVerification)
    }

    fun createVerification(verification : Verification) : Verification =
        verificationRepository.save(verification)
}