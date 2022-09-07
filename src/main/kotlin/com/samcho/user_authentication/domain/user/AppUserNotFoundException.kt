package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.core.exception.EntityNotFoundException

/**
 * 데이터 원천에서 AppUser Entity를 찾을 수 없을 경우 발생하는 오류
 */
class AppUserNotFoundException : EntityNotFoundException("App User")