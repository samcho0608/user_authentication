package com.samcho.user_authentication.domain.user.email_address

import com.samcho.user_authentication.domain.core.exception.InvalidFormatException

/**
 * 이메일 값의 형식에 맞지 않는 값이 전달됐을 경우 발생하는 예외
 */
class InvalidEmailAddressFormatException : InvalidFormatException(dataType = "Email")