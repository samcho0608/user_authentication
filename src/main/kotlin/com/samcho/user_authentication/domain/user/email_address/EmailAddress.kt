package com.samcho.user_authentication.domain.user.email_address

/**
 * 이메일 주소를 저장하는 VO
 * @throws InvalidEmailAddressException 이메일 주소값의 형식이 유효하지 않을 경우, 예외 처리
 */
data class EmailAddress(
    val emailAddress: String,
) {
    init {
        // 공식 이메일 Syntax 기준 (RFC 5322 Official Standard)

        // email 주소 최대 길이는 320
        if(emailAddress.length > 320) {
            throw InvalidEmailAddressException()
        }

        if(!Regex(emailPattern).matches(emailAddress)) {
            throw InvalidEmailAddressException()
        }
    }

    companion object {
        // Local-part
        // * 대소문자 알파벳
        // * 숫자
        // * 다음의 특수 문자 포함 : !#$%&'*+-/=?^_`{|}~
        // * .은 가능하나 처음, 마지막, 연속으로 2개 이상은 불가
        private const val localPattern = "([a-z0-9!#\\\$%&'*+/=?^_`{|}~-]+\\.{0,1}[a-z0-9!#\\\$%&'*+/=?^_`{|}~-]+)"

        // Domain
        // * 대소문자 알파벳
        // * 숫자
        // * hyphen
        // * 접미사 필수
        private const val domainPattern = "[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+"
        const val emailPattern = "^${localPattern}@${domainPattern}$"
    }
}
