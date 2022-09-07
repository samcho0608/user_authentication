package com.samcho.user_authentication.domain.core.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtFactory @Autowired constructor(
    @Value("\${user-auth.secret}")
    private val serverSecret: String,
    @Value("\${user-auth.issuer}")
    private val issuer: String,
) {
    fun createToken(sub : String, exp: Date, payload : Map<String, Any>? = null): String {
        val algo = Algorithm.HMAC256(serverSecret)
        val jwtBuilder = JWT.create()
            .withSubject(sub)
            .withExpiresAt(exp)
            .withIssuedAt(Date.from(Instant.now()))
            .withIssuer(issuer)

        if(payload != null) {
            jwtBuilder.withPayload(payload)
        }

        return jwtBuilder
            .sign(algo)
    }
}