package com.samcho.user_authentication.presentation.dto

import org.springframework.http.HttpStatus

data class SuccessResponseBody (
    var status: HttpStatus = HttpStatus.OK,
    var message: String = "Success",
    var data: Any? = null
)