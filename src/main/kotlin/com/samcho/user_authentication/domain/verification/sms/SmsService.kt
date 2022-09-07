package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SmsService @Autowired constructor(
    private val messageService: DefaultMessageService,
    @Value("\${coolsms.from-number}")
    private val fromNumber : String,
){

    /**
     * @throws SmsFailureException SMS 문자를 보낸 것에 실패하였을때 발생
     */
    fun sendVerificationSms(toNumber: String, code: VerificationCode) : SingleMessageSentResponse? {
        TODO("Not yet implemented")
    }
}