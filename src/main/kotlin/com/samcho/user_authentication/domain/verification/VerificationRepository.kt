package com.samcho.user_authentication.domain.verification

import com.samcho.user_authentication.domain.core.vo.Contact

interface VerificationRepository {
    fun save(verification: Verification) : Verification

    fun delete(verification: Verification)

    fun findById(id: Contact) : Verification?
}