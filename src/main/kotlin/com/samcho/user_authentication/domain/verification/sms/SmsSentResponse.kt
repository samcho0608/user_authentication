package com.samcho.user_authentication.domain.verification.sms

data class SmsSentResponse(
    val to: String,
    val from: String,
    val statusMessage: String,
    val messageId: String,
    val statusCode: String,
);
