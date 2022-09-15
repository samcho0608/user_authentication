package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode

class MockSmsService : SmsService{

    /**
     * @throws SmsFailureException SMS 문자를 보내는 것에 실패하였을때 발생
     */
    override fun sendVerificationSms(toNumber: String, code: VerificationCode) : SmsSentResponse {
        return SmsSentResponse(
            messageId = "0",
            to = toNumber,
            from = "01012341234",
            statusCode = "2000",
            statusMessage = "Sent verification successfully"
        )
    }
}