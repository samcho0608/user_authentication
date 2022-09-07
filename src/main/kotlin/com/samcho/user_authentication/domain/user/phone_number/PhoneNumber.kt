package com.samcho.user_authentication.domain.user.phone_number

import com.samcho.user_authentication.domain.core.vo.Contact

/**
 * 핸드폰 정보 VO
 */
data class PhoneNumber(
    val phoneNumber: String,
) : Contact {
    override val destination: String = phoneNumber

    init {
        if(!Regex("^[1-9]\\d{1,14}$").matches(phoneNumber)) {
            throw InvalidPhoneNumberFormatException()
        }
    }
}