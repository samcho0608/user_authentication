package com.samcho.user_authentication.domain.user.email_address

import com.samcho.user_authentication.domain.core.exceptions.InvalidFormatException

class InvalidEmailAddressException : InvalidFormatException(dataType = "Email")