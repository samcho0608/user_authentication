package com.samcho.user_authentication.domain.verification.verification_code

import com.samcho.user_authentication.domain.core.exception.InvalidFormatException

class InvalidVerificationCodeException : InvalidFormatException("Verification code")