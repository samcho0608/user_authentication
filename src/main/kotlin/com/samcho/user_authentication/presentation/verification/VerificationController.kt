package com.samcho.user_authentication.presentation.verification

import com.samcho.user_authentication.domain.verification.VerificationService
import com.samcho.user_authentication.domain.verification.sms.SmsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/verifications")
class VerificationController @Autowired constructor(
    private val verificationService: VerificationService,
    private val smsService: SmsService,
) {

    @PostMapping("/phone")
    fun sendVerification(@RequestBody request: PhoneVerificationRequest): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    @PostMapping("verify")
    fun verifyVerification(): ResponseEntity<Any>  {
        TODO("Not yet implemented")
    }
}