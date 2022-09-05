package com.samcho.user_authentication.domain.user.phone

/**
 * 국가 번호를 저장하는 VO
 * @throws [InvalidCountryCodeException] 국가 번호 값의 형식이 유효하지 않을 경우, 예외 처리
 */
data class CountryCode(
    val countryCode: String,
) {
    init {
        // E.164 공식 기준
        // * 1-3 자리 숫자
        if(!Regex("^\\d{1,3}$").matches(countryCode)) {
            throw InvalidCountryCodeException()
        }
    }
}