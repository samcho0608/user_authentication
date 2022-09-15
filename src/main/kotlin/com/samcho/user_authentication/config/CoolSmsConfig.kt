package com.samcho.user_authentication.config

import com.samcho.user_authentication.domain.verification.sms.CoolSmsService
import com.samcho.user_authentication.domain.verification.sms.SmsService
import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ConditionalOnProperty(name = ["sms_service.mock"], havingValue = "false")
class CoolSmsConfig @Autowired constructor(
    @Value("\${coolsms.api-key}")
    private val apiKey : String,
    @Value("\${coolsms.api-secret}")
    private val apiSecret : String,
    @Value("\${coolsms.url}")
    private val url : String,
    @Value("\${coolsms.from-number}")
    private val fromNumber : String,
) : WebMvcConfigurer {

    @Bean
    fun messageService() : DefaultMessageService {
        print(apiKey)
        print(apiSecret)
        print(url)
        print(fromNumber)

        return NurigoApp.initialize(
            apiKey,
            apiSecret,
            url,
        )
    }

    @Bean
    @ConditionalOnProperty(name = ["sms_service.mock"], havingValue = "false")
    fun smsService(messageService: DefaultMessageService) : SmsService = CoolSmsService(messageService, fromNumber)
}