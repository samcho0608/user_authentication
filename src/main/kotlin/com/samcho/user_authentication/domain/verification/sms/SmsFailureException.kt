package com.samcho.user_authentication.domain.verification.sms

/**
 * SMS 발송에 실패했을때 발생하는 예외
 */
class SmsFailureException(
    reason: String,
) : Exception("Failed to send SMS : $reason")