package com.samcho.user_authentication.data.user.repository

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.repository.AppUserRepository

class MemoryAppUserRepository : AppUserRepository {
    override fun findById(id: String): AppUser? = userDB[id]

    override fun findAppUserDetailById(id: String): AppUserDetail? {
        val user = userDB[id] ?: return null

        return object : AppUserDetail {
            override val id: String
                get() = user.id!!
            override val nicknm: String
                get() = user.nicknm
            override val email: EmailAddress
                get() = user.email!!
            override val name: String
                get() = user.name
            override val phone: PhoneNumber
                get() = user.phone!!
        }
    }

    override fun save(user: AppUser): AppUser {
        val newUserId = generateUserId()
        val newPhoneId = generatePhoneId()

        user.apply { id = newUserId }
        user.phone!!.apply { id = newPhoneId }
        phoneDB[newPhoneId] = user.phone!!
        userDB[newUserId] = user
        return user
    }

    override fun updatePasswordById(id: String, newPassword: String) {
        userDB[id]?.password= newPassword
    }

    /**
     *  테스트 시에만 사용되며, 저장된 데이터를 지움
     */
    internal fun clearDB() {
        phoneDB.clear()
        userDB.clear()
        userIdSequence = 0
        phoneIdSequence = 0
    }

    companion object {
        val userDB = mutableMapOf<String, AppUser>()
        var userIdSequence = 0
        val phoneDB = mutableMapOf<String, PhoneNumber>()
        var phoneIdSequence = 0

        fun generateUserId() = "USER_${"${userIdSequence++}".padStart(0, '0')}"
        fun generatePhoneId() = "PHONE_${"${phoneIdSequence++}".padStart(0, '0')}"
    }

}