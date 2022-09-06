package com.samcho.user_authentication.domain.user.phone_number

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PhoneNumberTest {

    @Test
    fun testValidPhoneNumber() {
        val number = "821012341234"

        val phoneNumber = PhoneNumber(phoneNumber = number,)

        assertEquals(number, phoneNumber.phoneNumber)
    }

    @Test
    fun testNonNumericPhoneNumber() {
        val number = "A8212341234"

        assertThrows(InvalidPhoneNumberFormatException::class.java) {
            PhoneNumber(number)
        }
    }

    @Test
    fun testZeroAtTheBeginningPhoneNumber() {
        val number = "08212341234"

        assertThrows(InvalidPhoneNumberFormatException::class.java) {
            PhoneNumber(number)
        }
    }

    @Test
    fun testPhoneNumberLength() {
        for (i in 0 until 20) {
            val chars = mutableListOf<String>()

            for (j in 0 until i) {
                chars.add("1")
            }

            val number = chars.joinToString("")
            if (number.length in 2..15) {
                assertEquals(number, PhoneNumber(number).phoneNumber)
            } else {
                assertThrows(InvalidPhoneNumberFormatException::class.java) {
                    PhoneNumber(number)
                }
            }
        }

        val number = "A8212341234"

        assertThrows(InvalidPhoneNumberFormatException::class.java) {
            PhoneNumber(number)
        }
    }
}