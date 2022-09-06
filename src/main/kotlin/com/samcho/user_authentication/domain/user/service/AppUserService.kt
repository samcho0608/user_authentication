package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.core.exception.NotEnoughArgumentException
import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.LogInFailureException
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

    /**
     * @param user 로그인 시 필요한 유저 식별 정보. 비밀번호와 email/nicknm/phoneNumber 3 값 중 하나는 포함되어야함.
     * @throws AppUserNotFoundException 제공된 유저 식별 정보와 일치하는 유저를 찾지 못한 경우에 발생
     * @throws LogInFailureException 제공된 비밀번호가 유저의 비밀번호와 일치하지 않은 경우에 발생
     * @throws NotEnoughArgumentException 로그인에 필요한 정보가 충분하게 제공되지 않은 경우 발생
     */
    fun logIn(user: AppUser): AppUser {
        if(!user.satisfiesLogInRequirements()) {
            throw NotEnoughArgumentException()
        }

        val loggedInUser =
            (if (user.phoneNumber != null) {
                appUserRepository.findByPhoneNumber(user.phoneNumber!!.phoneNumber)
            } else if (user.nicknm.isNotEmpty()) {
                appUserRepository.findByNicknm(user.nicknm)
            } else {
                appUserRepository.findByEmail(user.email!!.emailAddress)
            })
                ?: throw AppUserNotFoundException()

        if(loggedInUser.password != user.password) {
            throw LogInFailureException()
        }

        return loggedInUser
    }

    fun resetPassword(user: AppUser, newPassword: String) {
        if(!appUserRepository.existsById(user.id!!)) {
            throw AppUserNotFoundException()
        }
        appUserRepository.updatePasswordById(user.id!!, newPassword)
    }
}