package com.samcho.user_authentication.domain.core.exceptions

/**
 * 처리하려는 형식이 정해진 종류의 데이터 input 값이 맞지 않을 경우 발생하는 예외
 */
open class InvalidFormatException(
    dataType : String,
) : Exception("$dataType is in an invalid format")