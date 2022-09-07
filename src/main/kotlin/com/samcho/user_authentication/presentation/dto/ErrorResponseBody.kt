package com.samcho.user_authentication.presentation.dto

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorResponseBody(
    var timestamp: LocalDateTime = LocalDateTime.now(),
    var status : Int = HttpStatus.BAD_REQUEST.value(),
    var error: String? = null,
    var code: String = HttpStatus.BAD_REQUEST.name,
    var message: String = "Failed to process request",
)
