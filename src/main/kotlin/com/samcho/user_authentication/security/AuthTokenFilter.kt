package com.samcho.user_authentication.security

import com.auth0.jwt.JWT
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.samcho.user_authentication.domain.core.util.JwtFactory
import com.samcho.user_authentication.domain.core.util.logger
import com.samcho.user_authentication.presentation.ApiRoute
import com.samcho.user_authentication.presentation.dto.ErrorResponseBody
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthTokenFilter(
    private val jwtFactory: JwtFactory
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val allPermittedRoutes = listOf(
            // 유저 Collection 경로
            ApiRoute.LOG_IN,
            ApiRoute.USERS,

            // Verification Collection 경로
            ApiRoute.VERIFICATIONS,
            ApiRoute.PHONE_VERIFICATIONS,
            ApiRoute.VERIFY_PHONE_VERIFICATIONS
        )

        if(request.servletPath in allPermittedRoutes) {
            filterChain.doFilter(request, response)
        } else {
            request.getHeader(AUTHORIZATION)?.run {
                if(startsWith("Bearer ")) {
                    try {
                        val token = substring("Bearer ".length)
                        val verifier = JWT.require(jwtFactory.algorithm).build()
                        val decodedJWT = verifier.verify(token)
                        val username: String? = decodedJWT.subject
                        val authToken = UsernamePasswordAuthenticationToken(
                            username,
                            null, // credentials
                            listOf() // Authorities
                        )
                        SecurityContextHolder.getContext().authentication = authToken
                    } catch (e: Exception) {
                        logger().error("Authentication Token Exception : ${e.message}")
                        response.status = HttpStatus.FORBIDDEN.value()
                        response.contentType = APPLICATION_JSON_VALUE
//
                        jacksonObjectMapper()
                            .registerModule(JavaTimeModule())
                            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            .writeValue(
                                response.outputStream,
                                ErrorResponseBody(
                                    code = HttpStatus.FORBIDDEN.name,
                                    status = HttpStatus.FORBIDDEN.value(),
                                    error = e.message,
                                    message = "Access Token Denied"
                                )
                        )
                    }
                }
            }

            filterChain.doFilter(request, response)
        }
    }
}