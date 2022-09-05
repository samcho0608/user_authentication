package com.samcho.user_authentication.domain.user.phone

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CountryCodeTest {

    // Invalid Character Test

    /**
     * 숫자외의 글자가 있을 경우, [InvalidSubscriberNumberException] 오류 처리 확인
     */
    @Test
    fun testNonNumericSubscriberNumber() {
        val number = "+12"
        assertThrows(InvalidCountryCodeException::class.java) {
            CountryCode(number)
        }
    }

    // Length Test

    @Test
    fun testPhoneNumberLength() {
        for (i in 0.. 4) {
            val chars = mutableListOf<String>()
            for(j in 0 until i) {
                chars.add("0")
            }
            val number = chars.joinToString(separator = "")
            if (i in 1..3) {
                assertEquals(number, CountryCode(number).countryCode)
            } else {
                assertThrows(InvalidCountryCodeException::class.java) {
                    CountryCode(number)
                }
            }
        }
    }
}