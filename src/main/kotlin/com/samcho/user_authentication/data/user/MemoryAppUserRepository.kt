package com.samcho.user_authentication.data.user

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.repository.AppUserRepository

/**
 * AppUserRepository 클래스에 의존하는 클래스들의 단위 테스트를 위해 구현된 테스트용 클래스
 */
class MemoryAppUserRepository : AppUserRepository {
    override fun existsById(id: String): Boolean =
        userDB.containsKey(id)

    override fun existsByPhoneNumber(phoneNumber: String): Boolean =
        userDB.values.any { it.phoneNumber != null && it.phoneNumber!!.phoneNumber == phoneNumber }

    override fun findById(id: String): AppUser? = userDB[id]

    private fun appUserToAppUserDetail(user : AppUser): AppUserDetail {
        return object : AppUserDetail {
            override val id: String
                get() = user.id!!
            override val nicknm: String
                get() = user.nicknm
            override val email: EmailAddress
                get() = user.email!!
            override val name: String
                get() = user.name
            override val phoneNumber: PhoneNumber
                get() = user.phoneNumber!!
        }
    }

    override fun findAppUserDetailById(id: String): AppUserDetail? {
        val user = userDB[id] ?: return null
        return appUserToAppUserDetail(user)
    }

    override fun findAppUserDetailByEmail(email: String): AppUserDetail? {
        val user = userDB
            .values
            .find { it.email != null && it.email!!.emailAddress == email }
            ?: return null

        return appUserToAppUserDetail(user)
    }

    override fun findAppUserDetailByNicknm(nicknm: String): AppUserDetail? {
        val user = userDB
            .values
            .find { it.nicknm == nicknm }
            ?: return null

        return appUserToAppUserDetail(user)
    }

    override fun findAppUserDetailByPhoneNumber(phoneNumber: String): AppUserDetail? {
        val user = userDB
            .values
            .find { it.phoneNumber != null && it.phoneNumber!!.phoneNumber == phoneNumber }
            ?: return null

        return appUserToAppUserDetail(user)
    }

    override fun findByEmail(email: String): AppUser? =
        userDB.values.find { it.email!!.emailAddress == email }

    override fun findByNicknm(nicknm: String): AppUser? =
        userDB.values.find { it.nicknm == nicknm }

    override fun findByPhoneNumber(phoneNumber: String): AppUser? =
        userDB.values.find { it.phoneNumber!!.phoneNumber == phoneNumber }

    override fun save(user: AppUser): AppUser {
        val newUserId = generateUserId()

        user.apply { id = newUserId }
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
        userDB.clear()
        userIdSequence = 0
    }

    companion object {
        val userDB = mutableMapOf<String, AppUser>()
        var userIdSequence = 0

        fun generateUserId() = "USER_${"${userIdSequence++}".padStart(0, '0')}"
    }

}