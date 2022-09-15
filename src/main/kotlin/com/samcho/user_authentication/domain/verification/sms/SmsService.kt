package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode

interface SmsService {
    fun sendVerificationSms(toNumber: String, code: VerificationCode) : SmsSentResponse?
}