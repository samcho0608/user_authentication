package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.repository.AppUserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = ["integration-test"])
internal class AppUserServiceIntegrationTest{

    @Autowired private lateinit var appUserService: AppUserService
    @Autowired private lateinit var appUserRepository: AppUserRepository


    @AfterEach
    fun afterEach() {
        appUserRepository.deleteAll()
    }

    @Test
    fun signUp() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        appUserService.signUp(user)

        assertNotEquals(null, user.id)
    }

    @Test
    fun findUserDetailUserDoesNotExists() {
        assertThrows(AppUserNotFoundException::class.java) {

            appUserService.findUserDetail("USER_ID")
        }
    }
    @Test
    fun findUserDetailUserExists() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }
        appUserService.signUp(user)

        val foundUser = appUserService.findUserDetail(user.nicknm)

        assertNotEquals(null, foundUser)
        assertEquals(user.id, foundUser.id)
        assertEquals(user.nicknm, foundUser.nicknm)
        assertEquals(user.email, foundUser.email)
        assertEquals(user.phoneNumber, foundUser.phoneNumber)
        assertEquals(user.name, foundUser.name)
    }

    @Test
    fun resetPasswordUserDoesNotExist() {
        val newPassword = "new password"
        val phoneNumber = "821084273267"

        assertThrows(AppUserNotFoundException::class.java) {
            appUserService.resetPassword(
                AppUser().apply {
                    id = "USER_ID"
                    this.phoneNumber = PhoneNumber(phoneNumber)
                },
                newPassword
            )
        }
    }

    @Test
    fun resetPasswordUserExists() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }
        val savedUser = appUserService.signUp(user)

        val oldPassword = savedUser.password

        val newPassword = "new password"
        appUserService.resetPassword(user, newPassword)

        assertNotEquals(oldPassword, user.password)
    }
}