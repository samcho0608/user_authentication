package com.samcho.user_authentication.domain.user.service

import com.samcho.user_authentication.data.user.MemoryAppUserRepository
import com.samcho.user_authentication.domain.core.exception.NotEnoughArgumentException
import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.LogInFailureException
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class AppUserServiceTest {

    private val appUserRepository = MemoryAppUserRepository()
    private val appUserService = AppUserService(appUserRepository, BCryptPasswordEncoder())

    @AfterEach
    fun afterEach() {
        appUserRepository.clearDB()
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

            appUserService.findUserDetail(AppUser().apply { id = "USER_ID" })
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

        val foundUser = appUserService.findUserDetail(user)

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

        assertThrows(AppUserNotFoundException::class.java) {

            appUserService.resetPassword(AppUser().apply { id = "USER_ID" }, newPassword)
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
        appUserService.signUp(user)

        val newPassword = "new password"
        appUserService.resetPassword(user, newPassword)

        assertEquals(newPassword, user.password)
    }

    @Test
    fun logInWithSufficientData() {
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
        val logInRequests = listOf(
            AppUser().apply {
                email = EmailAddress("hgd1234@gmail.com")
                password = "hongGilDong1234"
            },
            AppUser().apply {
                nicknm = "요리번쩍조리번쩍"
                password = "hongGilDong1234"
            },
            AppUser().apply {
                password = "hongGilDong1234"
                phoneNumber = PhoneNumber(
                    "821012341234"
                )
            }
        )

        for (request in logInRequests) {
            val loggedInUser = appUserService.logIn(request)
            assertEquals(user, loggedInUser)
        }


    }

    @Test
    fun logInWithInsufficientIdentifier() {
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


        assertThrows(NotEnoughArgumentException::class.java) {
            appUserService.logIn(
                AppUser().apply {
                    name = "홍길동"
                    password = "hongGilDong1234"
                }
            )
        }

    }

    @Test
    fun logInWithNoPassword() {
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

        assertThrows(NotEnoughArgumentException::class.java) {
            appUserService.logIn(
                AppUser().apply {
                    email = EmailAddress("hgd1234@gmail.com")
                }
            )
        }
    }

    @Test
    fun logInWithWrongPassword() {
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

        assertThrows(LogInFailureException::class.java) {
            appUserService.logIn(
                AppUser().apply {
                    email = EmailAddress("hgd1234@gmail.com")
                    password = "11234"
                },
            )
        }
    }
}