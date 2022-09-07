package com.samcho.user_authentication.presentation.verification

import com.samcho.user_authentication.domain.core.util.JwtFactory
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.verification.Verification
import com.samcho.user_authentication.domain.verification.VerificationFailureException
import com.samcho.user_authentication.domain.verification.VerificationService
import com.samcho.user_authentication.domain.verification.sms.SmsFailureException
import com.samcho.user_authentication.domain.verification.sms.SmsService
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCodeGenerator
import com.samcho.user_authentication.interceptors.logger
import com.samcho.user_authentication.presentation.dto.ErrorResponseBody
import com.samcho.user_authentication.presentation.dto.SuccessResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@RestController
@RequestMapping("/verifications")
class VerificationController @Autowired constructor(
    private val verificationService: VerificationService,
    private val smsService: SmsService,
    private val jwtFactory: JwtFactory
) {

    @PostMapping("/phones")
    fun sendVerification(@RequestBody request: PhoneVerificationRequest): ResponseEntity<Any> {
        val verification = verificationService.createVerification(
            Verification().apply {
                verificationCode = VerificationCodeGenerator.generateVerificationCode()
                verificationChannel = PhoneNumber(request.run { countryCode + phoneNumber })
            }
        )

        return try {
            val phoneNumber = (verification.verificationChannel as PhoneNumber).phoneNumber
            smsService.sendVerificationSms(
                toNumber = "0${phoneNumber.substringAfter("82")}",
                code = verification.verificationCode!!
            )
            ResponseEntity
                .ok()
                .body(
                    SuccessResponseBody(
                        message = "Successfully sent verification code to request phone number"
                    )
                )
        } catch (e : SmsFailureException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("phones/verify")
    fun verifyPhoneVerification(@RequestBody request: VerifyPhoneVerificationRequest): ResponseEntity<Any>  {
        return try {
            val phoneNumber = request.run { countryCode + phoneNumber }
            verificationService.verifyVerification(
                Verification(
                    verificationChannel = PhoneNumber(phoneNumber),
                    verificationCode = VerificationCode(request.verificationCode)
                )
            )
            ResponseEntity
                .ok()
                .body(
                    SuccessResponseBody(
                        message = "Successfully verified the phone number",
                        data = jwtFactory.createToken(
                            sub = phoneNumber,
                            exp = Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)),
                        )
                    )
                )
        } catch (e : VerificationFailureException) {
            logger().error(e.message)

            ErrorResponseBody().apply {
                this.status
                this.message = e.message ?: ""
                this.error = "${e.javaClass}"
            }
            ResponseEntity.badRequest().build()
        }
    }
}