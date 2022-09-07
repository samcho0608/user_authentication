package com.samcho.user_authentication.domain.verification

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationService @Autowired constructor(
    private val verificationRepository: VerificationRepository
){

    fun verifyVerification(verification : Verification) {
        TODO("Not yet implemented")
    }

    fun createVerification(verification : Verification) : Verification {
        TODO("Not yet implemented")
    }
}