package com.samcho.user_authentication.domain.user.phone


/**
 * 가입자 번호를 저장하는 VO
 * @throws InvalidSubscriberNumberException 가입자 번호 값의 형식이 유효하지 않을 경우, 예외 처리
 */
data class SubscriberNumber(
    val subscriberNumber: String,
) {
    init {
        // E.164 공식 기준
        // * 12-14 자리 숫자
        if(!Regex("^\\d{12,14}$").matches(subscriberNumber)) {
            throw InvalidSubscriberNumberException()
        }
    }
}

// cc + subscriber
