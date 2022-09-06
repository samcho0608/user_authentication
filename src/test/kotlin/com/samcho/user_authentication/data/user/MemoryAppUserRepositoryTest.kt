package com.samcho.user_authentication.data.user

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class MemoryAppUserRepositoryTest {

    private val userRepo = MemoryAppUserRepository()

    @AfterEach
    fun afterEach() {
        userRepo.clearDB()
    }

    @Test
    fun save() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        val savedUser = userRepo.save(user)
        val foundUser = userRepo.findById(user.id!!)

        assertEquals(savedUser.id, foundUser!!.id)
    }

    @Test
    fun existsByIdUserExists() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        val savedUser = userRepo.save(user)

        assertEquals(true, userRepo.existsById(savedUser.id!!))
    }

    @Test
    fun existsByIdUserDoesNotExists() {
        assertEquals(false, userRepo.existsById("USER_0"))
    }

    @Test
    fun findAppUserDetailById() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        userRepo.save(user)

        val foundUser = userRepo.findAppUserDetailById(user.id!!)

        assertNotEquals(null, foundUser)
        assertEquals(user.id, foundUser!!.id)
        assertEquals(user.nicknm, foundUser.nicknm)
        assertEquals(user.email, foundUser.email)
        assertEquals(user.phoneNumber, foundUser.phoneNumber)
        assertEquals(user.name, foundUser.name)
    }

    @Test
    fun findAppUserDetailByIdNotFound() {
        val foundUser = userRepo.findAppUserDetailById("USER_ID")
        assertEquals(null, foundUser)
    }

    @Test
    fun updatePassword() {

        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        val savedUser = userRepo.save(user)
        val newPassword = "new password"

        userRepo.updatePasswordById(id =  savedUser.id!!, newPassword= newPassword)
        val targetUser = userRepo.findById(savedUser.id!!)

        assertEquals(newPassword, targetUser!!.password)

    }
}