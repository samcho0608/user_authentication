package com.samcho.user_authentication.security

import com.samcho.user_authentication.domain.core.util.JwtFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
// prePostEnabled : 함수들의 실행 전후로 처리하는 보안 설정을 가능케 해줌
// securedEnabled : @Secured annotation을 사용하게 해줌
// jsr250Enabled : @RoleAllowed annotation을 사용하게 해줌
class SecurityConfig @Autowired constructor(
    private val jwtFactory: JwtFactory
) {

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()



    @Bean
    fun authManager(
        http: HttpSecurity,
        bCryptPasswordEncoder: PasswordEncoder,
        userDetailsService: UserDetailsService
    ): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build()
    }

    @Bean
    fun filterChain(http : HttpSecurity, authManager : AuthenticationManager) : SecurityFilterChain {
        val userAuthFilter = UserAuthFilter(authManager, jwtFactory)
        userAuthFilter.setFilterProcessesUrl("/users/log-in")
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/users/mine/**").authenticated()
            .antMatchers("/users/log-in/**").permitAll()
            .and()
            .httpBasic()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(userAuthFilter)

        http.authorizeRequests()
            .anyRequest()
            .permitAll()

        return http.build()
    }
}