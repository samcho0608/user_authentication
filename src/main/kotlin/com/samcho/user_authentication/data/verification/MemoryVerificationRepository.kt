package com.samcho.user_authentication.data.verification

import com.samcho.user_authentication.domain.core.vo.Contact
import com.samcho.user_authentication.domain.verification.Verification
import com.samcho.user_authentication.domain.verification.VerificationRepository

class MemoryVerificationRepository : VerificationRepository {
    override fun <T : Contact> save(verification: Verification<T>): Verification<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Contact> delete(verification: Verification<T>) {
        TODO("Not yet implemented")
    }

    override fun <T : Contact> findById(id: T): Verification<T>? {
        TODO("Not yet implemented")
    }

    internal fun clearDB() {
        TODO("Not yet implemented")
    }
}