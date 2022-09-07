package com.samcho.user_authentication.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
// prePostEnabled : 함수들의 실행 전후로 처리하는 보안 설정을 가능케 해줌
// securedEnabled : @Secured annotation을 사용하게 해줌
// jsr250Enabled : @RoleAllowed annotation을 사용하게 해줌
class SecurityConfig @Autowired constructor(
) {

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()


}