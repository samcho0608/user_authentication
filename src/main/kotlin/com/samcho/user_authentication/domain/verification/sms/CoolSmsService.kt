package com.samcho.user_authentication.domain.verification.sms

import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import net.nurigo.sdk.message.exception.NurigoBadRequestException
import net.nurigo.sdk.message.exception.NurigoInvalidApiKeyException
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

class CoolSmsService @Autowired constructor(
    private val messageService: DefaultMessageService,
    private val fromNumber : String,
) : SmsService{

    /**
     * @throws SmsFailureException SMS 문자를 보내는 것에 실패하였을때 발생
     */
    override fun sendVerificationSms(toNumber: String, code: VerificationCode) : SmsSentResponse? {
        val msg = SmsVerificationMessage(
            from = fromNumber,
            to = toNumber,
            verificationCode = code
        )

        try {
            return messageService.sendOne(SingleMessageSendingRequest(msg.message))?.run {
                SmsSentResponse(
                    messageId = messageId,
                    to = to,
                    from = from,
                    statusCode = statusCode,
                    statusMessage = statusMessage
                )
            }
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