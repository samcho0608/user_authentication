package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone.Phone

/**
 * 유저 정보 Entity
 */
class User(
    var id: String,

    /**
     * 유저 닉네임. Unique
     */
    var nicknm: String,

    /**
     * 유저 이메일. Unique
     */
    var email: EmailAddress,
    /**
     * 유저 이름
     */
    var name: String,
    /**
     * 비밀번호. BCrypt로 암호화
     */
    var password: String,
    /**
     * 유저 전화번호
     */
    var phone: Phone,
)
