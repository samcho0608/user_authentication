package com.samcho.user_authentication.domain.user.phone

import com.samcho.user_authentication.domain.core.exceptions.InvalidFormatException

class InvalidSubscriberNumberException : InvalidFormatException("Subscriber Number")