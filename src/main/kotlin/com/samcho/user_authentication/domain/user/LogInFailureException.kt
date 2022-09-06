package com.samcho.user_authentication.domain.user

/**
 * 로그인 시 제공된 정보가 DB와 매칭되지 않아 생기는 예외
 * 예 :
 * * 제공된 비밀번호와 DB에 저장된 비밀번호가 일치하지 않는 경우
 */
class LogInFailureException : Exception("Failed to log in")