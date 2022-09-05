package com.samcho.user_authentication.domain.user.phone

/**
 * 핸드폰 정보 Entity
 */
class Phone(
    var id : String? = null,

    /**
     * 국가번호
     */
    var countryCode : CountryCode? = null,

    /**
     * 가입자 번호
     */
    var subscriberNumber : SubscriberNumber? = null,
)
