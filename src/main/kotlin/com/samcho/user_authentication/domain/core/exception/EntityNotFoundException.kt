package com.samcho.user_authentication.domain.core.exception

/**
 * 데이터 원천에 검색한 Entity가 없을 경우 발생하는 예외
 */
open class EntityNotFoundException(
    entityName: String,
) : Exception("$entityName not found")