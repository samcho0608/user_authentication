package com.samcho.user_authentication.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.samcho.user_authentication.domain.core.util.JwtFactory
import com.samcho.user_authentication.domain.core.util.logger
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAuthFilter constructor(
    private val authManager : AuthenticationManager,
    private val jwtFactory: JwtFactory,
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        request ?: throw object : AuthenticationException("No authenticaiton request provided") {}

        if(request.method != "POST") {
            throw AuthenticationServiceException("Authentication request method must be POST")
        }

        if(request.contentType != "application/json") {
            throw AuthenticationServiceException("Authentication request content type must be in JSON format")
        }
        val logInRequest = try {
            jacksonObjectMapper().readValue(
                StreamUtils.copyToString(request.inputStream, StandardCharsets.UTF_8),
                LogInRequest::class.java
            )
        } catch (e: Exception) {
            throw AuthenticationServiceException("Failed to parse request")
        }

        val username = logInRequest.username
        val password = logInRequest.password

        logger().info("Attempting to authenticate with username: $username and password: $password")

        val authToken = UsernamePasswordAuthenticationToken(username, password)
        return authManager.authenticate(authToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val random = SecureRandom.getInstanceStrong()

        val user = (authResult?.principal
                    ?: throw AuthenticationServiceException("Authentication Result Null")
                ) as User

        val accessTokenExp = Date.from(
            Instant.now().plus((random.nextInt(30) + 60).toLong(), ChronoUnit.MINUTES)
        )
        val refreshTokenExp = Date.from(Instant.now().plus(24, ChronoUnit.HOURS))
        val accessToken = jwtFactory.createToken(
            user.username,
            exp = accessTokenExp
        )
        val refreshToken = jwtFactory.createToken(
            user.username,
            exp = refreshTokenExp
        )


        response?.apply {
            contentType = APPLICATION_JSON_VALUE
            jacksonObjectMapper().writeValue(
                outputStream,
                mapOf(
                    "accessToken" to accessToken,
                    "refreshToken" to refreshToken
                )
            )
        }
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        failed: AuthenticationException?
    ) {
        super.unsuccessfulAuthentication(request, response, failed)
    }
}