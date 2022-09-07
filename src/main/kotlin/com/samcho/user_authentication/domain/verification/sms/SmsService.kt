package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import net.nurigo.sdk.message.exception.NurigoBadRequestException
import net.nurigo.sdk.message.exception.NurigoInvalidApiKeyException
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
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
        val msg = SmsVerificationMessage(
            from = fromNumber,
            to = toNumber,
            verificationCode = code
        )

        try {
            return messageService.sendOne(SingleMessageSendingRequest(msg.message))
        } catch (e: Exception) {

            val reason = when (e) {
                is NurigoBadRequestException -> "Invalid Request"
                is NurigoInvalidApiKeyException -> "Invalid Api Key"
                else -> "Unknown"
            }

            throw SmsFailureException(reason)
        }
    }
}