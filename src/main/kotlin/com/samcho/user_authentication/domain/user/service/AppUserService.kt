package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.UserNotFoundException
import com.samcho.user_authentication.domain.user.repository.AppUserRepository

class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    fun findUserDetail(user: AppUser): AppUserDetail =
        appUserRepository.findAppUserDetailById(user.id!!) ?: throw UserNotFoundException()

    fun findUser(user: AppUser): AppUser =
        appUserRepository.findById(user.id!!) ?: throw UserNotFoundException()

    fun signUp(user: AppUser): AppUser =
        appUserRepository.save(user)

    fun resetPassword(user: AppUser, newPassword: String) {
        if(!appUserRepository.existsById(user.id!!)) {
            throw UserNotFoundException()
        }
        appUserRepository.updatePasswordById(user.id!!, newPassword)
    }
}