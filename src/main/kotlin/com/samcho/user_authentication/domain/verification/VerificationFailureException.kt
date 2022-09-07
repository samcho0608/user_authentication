package com.samcho.user_authentication.domain.verification

class VerificationFailureException(
    reason: String
) : Exception("Failed to verify verification : $reason")