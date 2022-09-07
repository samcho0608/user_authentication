package com.samcho.user_authentication.domain.verification

import com.samcho.user_authentication.domain.core.exception.NotEnoughArgumentException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VerificationService @Autowired constructor(
    private val verificationRepository: VerificationRepository
){

    /**
     * OTP 인증 함수. 현재 등록된 인증 세션 중 [verification]과 일치하는 인증 세션이 있는지 확인함.
     *
     * 성공 시, 함수에 추가적인 동작 없음
     * @param verification 인증할 인증 세션 정보 (인증번호 및 전화번호)
     * @throws VerificationFailureException 인증에 실패하였을 경우 발생
     */
    fun verifyVerification(verification : Verification) {
        if(verification.verificationChannel == null || verification.verificationCode == null) {
            throw NotEnoughArgumentException()
        }

        val foundVerification = verificationRepository.findById(verification.verificationChannel)
            ?: throw VerificationFailureException("Verification not found")

        if(verification.verificationCode.code != foundVerification.verificationCode!!.code) {
            throw VerificationFailureException("Verification code mismatch")
        }

        if(foundVerification.isExpired()!!) {
            throw VerificationFailureException("Verification expired")
        }

        verificationRepository.delete(foundVerification)
    }

    /**
     * 새로운 OTP 인증 세션을 생성함
     * @param verification 생성될 OTP 인증 세션에 대한 정보
     * @throws NotEnoughArgumentException 인증번호와 연락처 둘 중 하나라도 누락될 경우 발생
     */
    fun createVerification(verification : Verification) : Verification {
        if(verification.verificationCode == null || verification.verificationChannel == null) {
            throw NotEnoughArgumentException()
        }


        return verificationRepository.save(verification)
    }

}