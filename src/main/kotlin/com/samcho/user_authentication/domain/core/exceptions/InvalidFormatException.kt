package com.samcho.user_authentication.domain.core.exceptions

open class InvalidFormatException(
    dataType : String,
) : Exception("$dataType is in an invalid format")