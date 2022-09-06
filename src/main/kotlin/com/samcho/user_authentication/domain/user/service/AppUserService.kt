package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.repository.AppUserRepository

/**
 * AppUser 클래스의 CRUD 비즈니스 로직을 담당하는 클래스
 */
class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    /**
     * 특정 한 유저 민감하지 않은 정보를 불러오는 함수.
     * @param user 검색할 대상이 되는 유저. 해당 유저의 id값을 기준으로 검색함.
     */
    fun findUserDetail(user: AppUser): AppUserDetail =
        appUserRepository.findAppUserDetailById(user.id!!) ?: throw AppUserNotFoundException()

    /**
     * 특정 한 유저 전체 정보를 불러오는 함수.
     * @param user 검색할 대상이 되는 유저. 해당 유저의 id값을 기준으로 검색함.
     */
    fun findUser(user: AppUser): AppUser =
        appUserRepository.findById(user.id!!) ?: throw AppUserNotFoundException()

    /**
     * 회원가입 함수
     * @param user 회원가입 시 등록될 유저 정보
     */
    fun signUp(user: AppUser): AppUser =
        appUserRepository.save(user)

    fun resetPassword(user: AppUser, newPassword: String) {
        if(!appUserRepository.existsById(user.id!!)) {
            throw AppUserNotFoundException()
        }
        appUserRepository.updatePasswordById(user.id!!, newPassword)
    }
}