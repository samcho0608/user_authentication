package com.samcho.user_authentication.config

import com.samcho.user_authentication.data.user.MemoryAppUserRepository
import com.samcho.user_authentication.data.verification.MemoryVerificationRepository
import com.samcho.user_authentication.domain.user.repository.AppUserRepository
import com.samcho.user_authentication.domain.verification.VerificationRepository
import com.samcho.user_authentication.domain.verification.sms.MockSmsService
import com.samcho.user_authentication.domain.verification.sms.SmsService
import com.samcho.user_authentication.interceptors.LogInterceptor
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class UserAuthenticationConfig : WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(name = ["app_user_repository.mock"], havingValue = "false")
    fun appUserRepository() : AppUserRepository = MemoryAppUserRepository()

    @Bean
    @ConditionalOnProperty(name = ["sms_service.mock"], havingValue = "true")
    fun smsService() : SmsService = MockSmsService()

    @Bean
    fun verificationRepository() : VerificationRepository = MemoryVerificationRepository()

    @Bean
    fun logInterceptor() : LogInterceptor = LogInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(logInterceptor())
            .addPathPatterns("/**")
    }
}