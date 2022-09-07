package com.samcho.user_authentication.domain.user.email_address

import com.samcho.user_authentication.domain.core.vo.Contact

/**
 * 이메일 주소를 저장하는 VO
 * @throws InvalidEmailAddressFormatException 이메일 주소값의 형식이 유효하지 않을 경우, 예외 처리
 */
data class EmailAddress(
    val emailAddress: String,
) : Contact {
    override val destination: String = emailAddress
    init {
        if(!isInEmailAddressFormat(emailAddress)) {
            throw InvalidEmailAddressFormatException()
        }
    }

    companion object {
        // 공식 이메일 Syntax 기준 (RFC 5322 Official Standard)

        // Local-part
        // * 대소문자 알파벳
        // * 숫자
        // * 다음의 특수 문자 포함 : !#$%&'*+-/=?^_`{|}~
        // * .은 가능하나 처음, 마지막, 연속으로 2개 이상은 불가
        private const val localPattern = "([a-z0-9!#\\\$%&'*+/=?^_`{|}~-]+\\.?[a-z0-9!#\\\$%&'*+/=?^_`{|}~-]+)"

        // Domain
        // * 대소문자 알파벳
        // * 숫자
        // * hyphen
        // * 접미사 필수
        private const val domainPattern = "[a-zA-Z0-9-]+\\.[a-zA-Z0-9-]+"
        private const val emailPattern = "^${localPattern}@${domainPattern}$"

        /**
         * 특정 string이 이메일 형식에 맞는지 알려주는 함수
         * @param input 형식을 체크하고자 하는 string
         * @return [input]이 이메일 형식에 맞는지에 대한 여부
         */
        fun isInEmailAddressFormat(input : String) : Boolean =
            input.length <= 320 && Regex(emailPattern).matches(input)
    }
}
