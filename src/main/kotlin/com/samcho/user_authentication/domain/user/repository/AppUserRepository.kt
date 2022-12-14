package com.samcho.user_authentication.domain.user.repository

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import org.jetbrains.annotations.TestOnly

/**
 * AppUser Entity의 데이터 원천과 상호작용하는 기능들의 집합
 */
interface AppUserRepository {
    fun existsById(id: String) : Boolean
    fun existsByPhoneNumber(phoneNumber: String) : Boolean
    fun findAppUserById(id: String): AppUser?
    fun findAppUserDetailById(id: String) : AppUserDetail?

    fun findAppUserDetailByEmail(email: String): AppUserDetail?

    fun findAppUserDetailByNicknm(nicknm: String): AppUserDetail?

    fun findAppUserDetailByPhoneNumber(phoneNumber: String): AppUserDetail?

    fun findByEmail(email: String): AppUser?

    fun findByNicknm(nicknm: String): AppUser?

    fun findByPhoneNumber(phoneNumber: String): AppUser?

    fun save(user: AppUser): AppUser
    fun updatePasswordById(id: String, newPassword: String)
    @TestOnly
    fun deleteAll()
}