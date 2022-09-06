package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber

interface AppUserDetail {
    val id: String
    val nicknm: String
    val email: EmailAddress
    val name: String
    val phone: PhoneNumber
}