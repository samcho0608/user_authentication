package com.samcho.user_authentication.domain.user.phone_number

import com.samcho.user_authentication.domain.core.vo.Contact
import javax.persistence.Embeddable

/**
 * 핸드폰 정보 VO
 */
@Embeddable
data class PhoneNumber(
    var phoneNumber: String,
) : Contact {
    override fun destination(): String = phoneNumber

    init {
        if(!isInPhoneNumberFormat(phoneNumber)) {
            throw InvalidPhoneNumberFormatException()
        }
    }

    companion object {
        private val phoneNumberRegex = Regex("^[1-9]\\d{1,14}$")

        /**
         * 특정 string이 전화번호 형식에 맞는지 알려주는 함수
         * @param input 형식을 체크하고자 하는 string
         * @return [input]이 전화번호 형식에 맞는지에 대한 여부
         */
        fun isInPhoneNumberFormat(input: String) : Boolean = phoneNumberRegex.matches(input)
    }
}