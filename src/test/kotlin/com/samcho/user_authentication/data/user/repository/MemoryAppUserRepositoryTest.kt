package com.samcho.user_authentication.data.user.repository

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.CountryCode
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.phone_number.SubscriberNumber
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
            phone = PhoneNumber().apply {
                countryCode = CountryCode("82")
                subscriberNumber = SubscriberNumber("1012341234")
            }
        }

        val savedUser = userRepo.save(user)
        val foundUser = userRepo.findById(user.id!!)

        assertEquals(savedUser.id, foundUser!!.id)
    }

    @Test
    fun findAppUserDetailById() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phone = PhoneNumber().apply {
                countryCode = CountryCode("82")
                subscriberNumber = SubscriberNumber("1012341234")
            }
        }

        userRepo.save(user)

        val foundUser = userRepo.findAppUserDetailById(user.id!!)

        assertNotEquals(null, foundUser)
    }

    @Test
    fun findAppUserDetailByIdNotFound() {
        val foundUser = userRepo.findAppUserDetailById("USER_ID")

        assertEquals(null, foundUser)
    }
}