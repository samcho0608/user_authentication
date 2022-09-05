package com.samcho.user_authentication.domain.user.phone

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SubscriberNumberTest {

    // Invalid Character Test

    /**
     * 숫자외의 글자가 있을 경우, [InvalidSubscriberNumberException] 오류 처리 확인
     */
    @Test
    fun testNonNumericSubscriberNumber() {
        val number = "0q012341234"
        assertThrows(InvalidSubscriberNumberException::class.java) {
            SubscriberNumber(number)
        }
    }

    // Length Test

    @Test
    fun testPhoneNumberLength() {
        for (i in 11.. 15) {
            val chars = mutableListOf<String>()
            for(j in 0 until i) {
                chars.add("0")
            }
            val number = chars.joinToString(separator = "")
            if (i in 12..14) {
                assertEquals(number, SubscriberNumber(number).subscriberNumber)
            } else {
                assertThrows(InvalidSubscriberNumberException::class.java) {
                    SubscriberNumber(number)
                }
            }
        }
    }
}