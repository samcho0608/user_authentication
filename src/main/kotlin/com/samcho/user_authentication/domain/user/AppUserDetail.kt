package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber

interface AppUserDetail {
    val id: String
    var nicknm: String
    var email: EmailAddress
    var name: String
    var phone: PhoneNumber
}