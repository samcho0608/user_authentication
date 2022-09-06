package com.samcho.user_authentication.domain.user.phone_number

import com.samcho.user_authentication.domain.core.exceptions.InvalidFormatException

class InvalidPhoneNumberException : InvalidFormatException("Phone Number")