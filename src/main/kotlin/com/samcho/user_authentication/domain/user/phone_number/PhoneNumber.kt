package com.samcho.user_authentication.domain.user.phone_number

/**
 * 핸드폰 정보 VO
 */
data class PhoneNumber(
    val phoneNumber: String
) {
    init {
        if(!Regex("^[1-9]\\d{1,14}$").matches(phoneNumber)) {
            throw InvalidPhoneNumberException()
        }
    }
}