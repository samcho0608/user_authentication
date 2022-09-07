package com.samcho.user_authentication.presentation.verification

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.verification.Verification
import com.samcho.user_authentication.domain.verification.VerificationService
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCodeGenerator
import com.samcho.user_authentication.interceptors.logger
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@AutoConfigureMockMvc
internal class VerificationControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val verificationService: VerificationService
) {

    @Test
    fun sendVerification() {
        mockMvc.perform(
            post("/verifications/phones",)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    jacksonObjectMapper().writeValueAsString(
                        mapOf(
                            "countryCode" to "82",
                            "phoneNumber" to "1084273267"
                        )
                    )
                )
        )
            .andDo { logger().debug("RECEIVED REQUEST : ${it.request.contentAsString}") }
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("Success")))
    }

    @Test
    fun verifyPhoneVerification() {
        val generatedCode = VerificationCodeGenerator.generateVerificationCode()

        val verification = Verification().apply {
            verificationChannel = PhoneNumber("821084273267")
            verificationCode = generatedCode
            expiration = Timestamp.from(Instant.now().plus(10, ChronoUnit.MINUTES))
        }
        verificationService.createVerification(verification = verification)


        mockMvc.perform(
            post("/verifications/phones/verify",)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    jacksonObjectMapper().writeValueAsString(
                        mapOf(
                            "verificationCode" to generatedCode.code,
                            "countryCode" to "82",
                            "phoneNumber" to "1084273267"
                        )
                    )
                )
        )
            .andDo { logger().debug("RECEIVED REQUEST : ${it.request.contentAsString}") }
            .andExpect(status().isOk)
            .andDo { logger().info("RECEIVED RESPONSE : ${it.response.contentAsString}") }
            .andExpect(content().string(containsString("Success")))
    }
}