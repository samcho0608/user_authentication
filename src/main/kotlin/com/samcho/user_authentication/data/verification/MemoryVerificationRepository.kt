package com.samcho.user_authentication.data.verification

import com.samcho.user_authentication.domain.core.vo.Contact
import com.samcho.user_authentication.domain.verification.Verification
import com.samcho.user_authentication.domain.verification.VerificationRepository

class MemoryVerificationRepository : VerificationRepository {
    override fun save(verification: Verification): Verification {
        verificationDB[verification.verificationChannel!!] = verification
        return verificationDB[verification.verificationChannel]!!
    }

    override fun delete(verification: Verification) {
        verificationDB.remove(verification.verificationChannel)
    }

    override fun findById(id: Contact): Verification? =
        verificationDB[id]

    internal fun clearDB() {
        verificationDB.clear()
    }

    companion object {
        val verificationDB = mutableMapOf<Contact, Verification>()
        val a = listOf<String>().listIterator()
    }
}