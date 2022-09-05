package com.samcho.user_authentication.domain.user.email_address

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EmailAddressTest {

    // At Sign Test
    /**
     * `@`가 없을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithoutAtSign() {
        val invalidEmail = "samcho9968gmail.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    // Local Part Test

    /**
     * Local Part가 없을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithNonAlphanumericLanguageLocal() {
        val invalidEmail = "조성민@gmail.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Local Part가 없을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithoutLocalPart() {
        val invalidEmail = "@gmail.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Local Part에 .이 연속으로 두번 있을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithConsecutiveDotsInLocalPart() {
        val invalidEmail = "samcho..9968@gmail.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Local Part 맨 앞에 .이 있을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithDotAtTheBeginningOfLocalPart() {
        val invalidEmail = "samcho..9968@gmail.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Local Part 맨 뒤에 .이 있을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithDotAtTheEndOfLocalPart() {
        val invalidEmail = "samcho.9968.@gmail.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Local Part 명에 특수문자가 있을 경우, 정상 작동 확인
     */
    @Test
    fun testAnEmailWithSpecialCharacterInLocalPart() {
        val validEmail = "samcho!9968@gmail.com"
        val email = EmailAddress(validEmail)
        assertEquals(validEmail, email.emailAddress)
    }

    // Domain Part Test

    /**
     * Domain Part 전체가 없을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithoutDomainPart() {
        val invalidEmail = "samcho9968@"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Domain name이 없을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithoutDomainName() {
        val invalidEmail = "samcho9968@.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Domain 주소에 -이 있을 경우, 정상 작동 확인
     */
    @Test
    fun testAnEmailWithHyphenInDomain() {
        val validEmail = "samcho!9968@gma-il.com"
        val email = EmailAddress(validEmail)
        assertEquals(validEmail, email.emailAddress)
    }

    /**
     * Domain TLD이 없을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testAnEmailWithoutDomainSuffix() {
        val invalidEmail = "samcho9968@gmail"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * Domain에 알파벳이나 숫자가 아닌 언어가 있을 경우, [InvalidEmailAddressException] 오류 처리 확인
     */
    @Test
    fun testNonAlphanumericLanguageDomain() {
        val invalidEmail = "samcho9968@네이버.com"
        assertThrows(InvalidEmailAddressException::class.java) {
            EmailAddress(invalidEmail)
        }
    }

    /**
     * 정상적인 이메일 주소일 경우, 정상 작동 확인
     */
    @Test
    fun testAValidEmail() {
        val validEmail = "samcho9968@gmail.com"

        val email = EmailAddress(validEmail)
        assertEquals(validEmail, email.emailAddress)
    }
}