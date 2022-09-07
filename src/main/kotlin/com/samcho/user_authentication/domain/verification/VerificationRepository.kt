package com.samcho.user_authentication.domain.verification

import com.samcho.user_authentication.domain.core.vo.Contact

interface VerificationRepository {
    fun <T: Contact>save(verification: Verification<T>) : Verification<T>

    fun <T: Contact>delete(verification: Verification<T>)

    fun <T: Contact>findById(id: T) : Verification<T>?
}