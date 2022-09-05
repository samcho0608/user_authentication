package com.samcho.user_authentication.domain.user.phone

class Phone(
    var phoneNumberId : String,

    /**
     * 국가번호
     */
    var countryCode : CountryCode,

    /**
     * 수신자 번호
     */
    var subscriberNumber : SubscriberNumber,
)
