package com.samcho.user_authentication.domain.user.phone_number

import com.samcho.user_authentication.domain.core.exception.InvalidFormatException

/**
 * 전화번호 값의 형식에 맞지 않는 값이 전달됐을 경우 발생하는 예외
 */
class InvalidPhoneNumberFormatException : InvalidFormatException("Phone Number")