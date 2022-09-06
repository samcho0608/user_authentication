package com.samcho.user_authentication.domain.verification

import com.samcho.user_authentication.domain.core.vo.Contact
import com.samcho.user_authentication.domain.verification.verification_code.VerificationCode
import java.sql.Timestamp
import java.time.Instant

/**
 * 인증 정보 Entity
 * @param T 인증을 보낼 [Contact] (연락처)의 종류
 * @param verificationChannel [verificationCode]를 받을 타겟.
 *
 * 멤버변수 destination(전화번호 혹은 이메일 주소) 값이 ID로 사용됨
 * @param verificationCode [verificationChannel]로 보낼 인증 번호
 * @param expiration 만료일
 */
data class Verification<T : Contact> (
    val verificationChannel: T,
    val verificationCode : VerificationCode,
    val expiration : Timestamp,
) {
    fun isExpired() = expiration.before(Timestamp.from(Instant.now()))
}