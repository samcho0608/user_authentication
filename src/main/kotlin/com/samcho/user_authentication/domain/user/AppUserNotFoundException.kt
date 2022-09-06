package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.core.exception.EntityNotFoundException

class AppUserNotFoundException : EntityNotFoundException("App User")