package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.repository.AppUserRepository

class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    fun findUserDetail(user: AppUser): AppUserDetail =
        appUserRepository.findAppUserDetailById(user.id!!) ?: throw AppUserNotFoundException()

    fun findUser(user: AppUser): AppUser =
        appUserRepository.findById(user.id!!) ?: throw AppUserNotFoundException()

    fun signUp(user: AppUser): AppUser =
        appUserRepository.save(user)

    fun resetPassword(user: AppUser, newPassword: String) {
        if(!appUserRepository.existsById(user.id!!)) {
            throw AppUserNotFoundException()
        }
        appUserRepository.updatePasswordById(user.id!!, newPassword)
    }
}