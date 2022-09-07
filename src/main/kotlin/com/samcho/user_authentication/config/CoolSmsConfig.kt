package com.samcho.user_authentication.config

import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CoolSmsConfig @Autowired constructor(
    @Value("\${coolsms.api-key}")
    private val apiKey : String,
    @Value("\${coolsms.api-secret}")
    private val apiSecret : String,
    @Value("\${coolsms.url}")
    private val url : String,
) : WebMvcConfigurer {

    @Bean
    fun messageService() : DefaultMessageService = NurigoApp.initialize(
        apiKey,
        apiSecret,
        url,
    )
}