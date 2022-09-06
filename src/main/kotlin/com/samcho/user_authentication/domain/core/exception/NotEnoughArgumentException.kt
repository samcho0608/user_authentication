package com.samcho.user_authentication.domain.core.exception

/**
 * 함수 동작에 필요한 인자 값들이 다 전달되지 않았을때 발생하는 예외
 * 예 :
 * * 로그인 시 비밀번호가 제공되지 않음
 */
class NotEnoughArgumentException : Exception("Not enough arguments")