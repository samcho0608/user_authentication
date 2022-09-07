package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import net.nurigo.sdk.message.model.Message


class SmsVerificationMessage (
    from : String,
    to : String,
    verificationCode : VerificationCode
) {
    val message: Message = Message(
        from = from,
        to = to,
        text = "인증번호는 [${verificationCode.code}] 입니다"
    )
}