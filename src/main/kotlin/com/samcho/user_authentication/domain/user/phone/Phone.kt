package com.samcho.user_authentication.domain.user.phone

/**
 * 핸드폰 정보 Entity
 */
class Phone(
    var id : String,

    /**
     * 국가번호
     */
    var countryCode : CountryCode,

    /**
     * 가입자 번호
     */
    var subscriberNumber : SubscriberNumber,
)
