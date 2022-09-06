package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.repository.AppUserRepository

class AppUserService(
    private val appUserRepository: AppUserRepository
) {

    fun findUserDetail(user: AppUser): AppUserDetail? {
        TODO("Not yet implemented")
    }

    fun findUser(user: AppUser): AppUser? {
        TODO("Not yet implemented")
    }

    fun signUp(user: AppUser): AppUser {
        TODO("Not yet implemented")
    }

    fun resetPassword(user: AppUser, newPassword: String) {
        TODO("Not yet implemented")
    }
}