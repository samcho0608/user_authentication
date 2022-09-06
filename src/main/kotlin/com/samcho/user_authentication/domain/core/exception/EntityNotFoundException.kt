package com.samcho.user_authentication.domain.core.exception

open class EntityNotFoundException(
    entityName: String,
) : Exception("$entityName not found")