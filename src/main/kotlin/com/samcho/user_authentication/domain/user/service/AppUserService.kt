package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.core.exception.NotEnoughArgumentException
import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.repository.AppUserRepository
import com.samcho.user_authentication.domain.core.util.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * AppUser 클래스의 CRUD 비즈니스 로직을 담당하는 클래스
 */
@Service
class AppUserService @Autowired constructor(
    private val appUserRepository: AppUserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {

    private fun validateIdSpecifiedAppUser(user: AppUser) {
        if(user.id == null) {
            throw NotEnoughArgumentException()
        }
    }


    /**
     * 특정 한 유저 민감하지 않은 정보를 불러오는 함수.
     * @param username 검색할 대상이 되는 유저의 식별자. 해당 유저의 id값을 기준으로 검색함.
     */
    fun findUserDetail(username: String): AppUserDetail {
        return (if(PhoneNumber.isInPhoneNumberFormat(username)) {
            appUserRepository.findAppUserDetailByPhoneNumber(username).also {
                logger().info(it.toString())
            }
        } else if(EmailAddress.isInEmailAddressFormat(username)) {
            appUserRepository.findAppUserDetailByEmail(username).also {
                logger().info(it.toString())
            }
        } else { // 닉네임 혹은 User ID로 간주
            // ID 검색이 속도가 더 빠름
            appUserRepository.findAppUserDetailById(username).also {
                logger().info(it.toString())
            }
                ?:
                appUserRepository.findAppUserDetailByNicknm(username).also {
                    logger().info(it.toString())
                }
        }) ?: throw AppUserNotFoundException()
    }

    /**
     * 특정 한 유저 전체 정보를 불러오는 함수.
     * @param username 검색할 대상이 되는 유저의 식별자. 해당 유저의 ID나 전화번호 값을 기준으로 검색함.
     * @throws AppUserNotFoundException user를 찾지 못했을 경우 발생
     */
    fun findUser(username: String): AppUser {
        return (if(PhoneNumber.isInPhoneNumberFormat(username)) {
            appUserRepository.findByPhoneNumber(username).also {
                logger().info(it.toString())
            }
        } else if(EmailAddress.isInEmailAddressFormat(username)) {
            appUserRepository.findByEmail(username).also {
                logger().info(it.toString())
            }
        } else { // 닉네임 혹은 User ID로 간주
            // ID 검색이 속도가 더 빠름
            appUserRepository.findAppUserById(username).also {
                logger().info(it.toString())
            }
                ?:
                appUserRepository.findByNicknm(username).also {
                    logger().info(it.toString())
                }
        }) ?: throw AppUserNotFoundException()
    }

    fun findUser(user: AppUser): AppUser {
        validateIdSpecifiedAppUser(user)
        return appUserRepository.findAppUserById(user.id!!) ?: throw AppUserNotFoundException()
    }

    /**
     * 회원가입 함수
     * @param user 회원가입 시 등록될 유저 정보
     */
    @Transactional
    fun signUp(user: AppUser): AppUser =
        appUserRepository.save(user.apply {
            password = passwordEncoder.encode(password)
        })

    /**
     * 비밀번호 재설정 함수
     * @param user 비밀번호를 재설정할 유저
     * @param newPassword 새로운 비밀번호
     * @throws AppUserNotFoundException 제공된 정보에 일치하는 유저를 찾을 수 없었을때 발생
     */
    @Transactional
    fun resetPassword(user: AppUser, newPassword: String) {
        if(user.phoneNumber == null) {
            throw NotEnoughArgumentException()
        }

        if(!appUserRepository.existsByPhoneNumber(user.phoneNumber!!.phoneNumber)) {
            throw AppUserNotFoundException()
        }

        val foundUser = appUserRepository.findAppUserDetailByPhoneNumber(user.phoneNumber!!.phoneNumber)
        appUserRepository.updatePasswordById(foundUser!!.id, passwordEncoder.encode(newPassword))
    }
    override fun loadUserByUsername(username: String?): UserDetails {
        username ?: throw NotEnoughArgumentException()

        val appUser = findUser(username)

        return User(
            appUser.nicknm,
            appUser.password,
            mutableListOf() // GrantedAuthorities
        )
    }
}