package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.core.util.IdGenerator

class UserIdGenerator() : IdGenerator(entityName = "USER")