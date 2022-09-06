package com.samcho.user_authentication.domain.user.repository

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail

interface AppUserRepository {
    fun existsById(id: String) : Boolean
    fun findById(id: String): AppUser?
    fun findAppUserDetailById(id: String) : AppUserDetail?
    fun save(user: AppUser): AppUser
    fun updatePasswordById(id: String, newPassword: String)
}