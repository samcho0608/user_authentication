package com.samcho.user_authentication.domain.core.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.reflect.typeOf

@Component
class JwtFactory @Autowired constructor(
    @Value("\${user-auth.secret}")
    private val serverSecret: String,
    @Value("\${user-auth.issuer}")
    private val issuer: String,
) {

    val algorithm : Algorithm= Algorithm.HMAC256(serverSecret)
    private final val random: SecureRandom = SecureRandom.getInstanceStrong()
    val accessTokenExp: Date = Date.from(
        Instant.now().plus((random.nextInt(30) + 60).toLong(), ChronoUnit.MINUTES)
    )
    val refreshTokenExp: Date = Date.from(Instant.now().plus(24, ChronoUnit.HOURS))

    /**
     * 인자 값을 토대로 JWT를 생성함
     * @param sub JWT의 Subject
     * @param exp JWT의 만료일
     * @param payload JWT의 Paylod
     * @param isAccessToken 참이면 AccessToken의 만료일로 생성
     * @param isRefreshToken 참이면 RefreshToken의 만료일로 생성
     * @return 인자 값을 토대로 생성된 JWT
     * @throws IllegalArgumentException [isAccessToken]와 [isRefreshToken]가 모두 참이면 발생
     */
    fun createToken(
        sub : String,
        exp: Date? = null,
        payload : Map<String, Any>? = null,
        isAccessToken : Boolean = false,
        isRefreshToken : Boolean = false,
    ): String {
        if(isAccessToken && isRefreshToken) {
            throw IllegalArgumentException("JWT cannot be both access token and refresh token")
        }

        val jwtBuilder = JWT.create()
            .withSubject(sub)
            .withExpiresAt(
                when{
                    isAccessToken -> accessTokenExp
                    isRefreshToken -> refreshTokenExp
                    else -> exp!!
                }
            )
            .withIssuedAt(Date.from(Instant.now()))
            .withIssuer(issuer)

        if(payload != null) {
            jwtBuilder.withPayload(payload)
        }

        return jwtBuilder
            .sign(algorithm)
    }
}