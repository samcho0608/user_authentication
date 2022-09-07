package com.samcho.user_authentication.config

import com.samcho.user_authentication.data.user.MemoryAppUserRepository
import com.samcho.user_authentication.data.verification.MemoryVerificationRepository
import com.samcho.user_authentication.domain.user.repository.AppUserRepository
import com.samcho.user_authentication.domain.verification.VerificationRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class UserAuthenticationConfig : WebMvcConfigurer {

    @Bean
    fun appUserRepository() : AppUserRepository = MemoryAppUserRepository()

    @Bean
    fun verificationRepository() : VerificationRepository = MemoryVerificationRepository()
}