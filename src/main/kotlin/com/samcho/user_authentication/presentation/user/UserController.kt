package com.samcho.user_authentication.presentation.user

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.samcho.user_authentication.domain.core.exception.EntityNotFoundException
import com.samcho.user_authentication.domain.core.util.JwtFactory
import com.samcho.user_authentication.domain.core.util.logger
import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserNotFoundException
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.service.AppUserService
import com.samcho.user_authentication.presentation.ApiRoute
import com.samcho.user_authentication.presentation.dto.ErrorResponseBody
import com.samcho.user_authentication.presentation.dto.SuccessResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class UserController @Autowired constructor(
    private val appUserService: AppUserService,
    private val jwtFactory: JwtFactory,
) {

    private fun errorResponseOnBadAuth(authorization: String): ResponseEntity<Any>? {
        if(!authorization.startsWith("Bearer ")) {
            logger().error("Authentication Token Not Provided")
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                    ErrorResponseBody(
                        code = HttpStatus.FORBIDDEN.name,
                        status = HttpStatus.FORBIDDEN.value(),
                        error = "Authentication Token Not Provided",
                        message = "Authentication Token Not Provided"
                    )
                )
        }

        return null
    }

    private fun decodeSubject(authorization : String) : String? {
        val token = authorization.substring("Bearer ".length)
        val verifier = JWT.require(jwtFactory.algorithm).build()
        val decodedJWT = verifier.verify(token)
        return decodedJWT.subject
    }

    @GetMapping(ApiRoute.USERS_MINE)
    fun getMyInfo(principal: Principal) : ResponseEntity<Any> {
        return try {

            val user = appUserService.findUserDetail(
                AppUser(
                    id = principal.name
                )
            )
            ResponseEntity
                .ok()
                .body(
                    SuccessResponseBody().apply {
                        this.status = HttpStatus.OK
                        this.message = "Successfully found user"
                        this.data = user
                    }
                )
        } catch (e: Exception) {
            when(e) {
                is AppUserNotFoundException ->
                    ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                            ErrorResponseBody().apply {
                                this.status = HttpStatus.NOT_FOUND.value()
                                this.code = HttpStatus.NOT_FOUND.name
                                this.message = e.message ?: "Not Found"
                                this.error = e.message
                            }
                        )
                else ->
                    ResponseEntity
                        .badRequest()
                        .body(
                            ErrorResponseBody().apply {
                                this.status = HttpStatus.BAD_REQUEST.value()
                                this.code = HttpStatus.BAD_REQUEST.name
                                this.message = e.message ?: "Bad Request"
                                this.error = e.message

                            }
                        )
            }
        }
    }

    @PostMapping(ApiRoute.USERS)
    fun signUp(
        @RequestHeader(AUTHORIZATION) authorization: String,
        @RequestBody request: SignUpRequest
    ): ResponseEntity<Any> {
        return authorization.run {
            val errorResponse = errorResponseOnBadAuth(this)
            if(errorResponse != null) {
                return errorResponse
            }

            try {
                val phoneNumber: String? = decodeSubject(authorization)

                val savedUser = appUserService.signUp(
                    AppUser().apply {
                        this.name = request.name
                        this.nicknm = request.nickname
                        this.email = EmailAddress(request.email)
                        this.phoneNumber = PhoneNumber(phoneNumber!!)
                        this.password = request.password
                    }
                )

                ResponseEntity
                    .created(URI("${ApiRoute.USERS}/${savedUser.id!!}"))
                    .body(
                        SuccessResponseBody().apply {
                            this.status = HttpStatus.CREATED
                            this.message = "Successfully created a new user"
                            this.data = savedUser
                        }
                    )

            } catch (e: Exception) {
                logger().error("Access Token Denied : ${e.message}")
                ResponseEntity
                    .badRequest()
                    .body(
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

    @PutMapping(ApiRoute.RESET_PASSWORD)
    fun resetPassword(@RequestHeader(AUTHORIZATION) authorization: String, @RequestBody request: ResetPasswordRequest): ResponseEntity<Any> {
        return authorization.run {
            val errorResponse = errorResponseOnBadAuth(this)
            if(errorResponse != null) {
                return errorResponse
            }

            try {
                val phoneNumber: String? = decodeSubject(this)

                appUserService.resetPassword(
                    AppUser(
                        phoneNumber = PhoneNumber(phoneNumber!!)
                    ),
                    newPassword = request.newPassword
                )

                ResponseEntity
                    .ok()
                    .body(
                        SuccessResponseBody().apply {
                            this.status = HttpStatus.OK
                            this.message = "Successfully reset password"
                        }
                    )

            } catch (e: JWTVerificationException) {
                logger().error("Access Token Denied : ${e.message}")
                ResponseEntity
                    .badRequest()
                    .body(
                        ErrorResponseBody(
                            code = HttpStatus.FORBIDDEN.name,
                            status = HttpStatus.FORBIDDEN.value(),
                            error = e.message,
                            message = "Access Token Denied"
                        )
                    )
            } catch (e: Exception) {
                logger().error("Bad Request : ${e.message}")
                ResponseEntity
                    .badRequest()
                    .body(
                        ErrorResponseBody(
                            code = HttpStatus.BAD_REQUEST.name,
                            status = HttpStatus.BAD_REQUEST.value(),
                            error = e.message,
                            message = "Bad Request : ${e.message}"
                        )
                    )
            }
        }
    }

    @GetMapping(ApiRoute.REFRESH_TOKEN)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val authorization = request.getHeader(AUTHORIZATION)
        if(authorization == null) {
            logger().error("Authentication Token Not Provided")
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                    ErrorResponseBody(
                        code = HttpStatus.FORBIDDEN.name,
                        status = HttpStatus.FORBIDDEN.value(),
                        error = "Authentication Token Not Provided",
                        message = "Authentication Token Not Provided"
                    )
                )
        }

        return authorization.run {
            val errorResponse = errorResponseOnBadAuth(this)
            if(errorResponse != null) {
                return errorResponse
            }
            try {
                val id: String? = decodeSubject(this)

                val user = appUserService.findUser(AppUser(id=id))

                val accessToken = jwtFactory.createToken(
                    user.id!!,
                    isAccessToken = true,
                )

                ResponseEntity
                    .ok()
                    .body(
                        SuccessResponseBody().apply {
                            this.status = HttpStatus.OK
                            this.message = "Successfully reset password"
                            this.data = mapOf(
                                "accessToken" to accessToken
                            )
                        }
                    )

            } catch (e: JWTVerificationException) {
                logger().error("Access Token Denied : ${e.message}")
                ResponseEntity
                    .badRequest()
                    .body(
                        ErrorResponseBody(
                            code = HttpStatus.FORBIDDEN.name,
                            status = HttpStatus.FORBIDDEN.value(),
                            error = e.message,
                            message = "Access Token Denied"
                        )
                    )
            } catch (e: EntityNotFoundException) {
                logger().error("Entity Not Found : ${e.message}")
                ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                        ErrorResponseBody(
                            code = HttpStatus.NOT_FOUND.name,
                            status = HttpStatus.NOT_FOUND.value(),
                            error = e.message,
                            message = "Entity Not Found : ${e.message}"
                        )
                    )
            }catch (e: Exception) {
                logger().error("Bad Request : ${e.message}")
                ResponseEntity
                    .badRequest()
                    .body(
                        ErrorResponseBody(
                            code = HttpStatus.BAD_REQUEST.name,
                            status = HttpStatus.BAD_REQUEST.value(),
                            error = e.message,
                            message = "Bad Request : ${e.message}"
                        )
                    )
            }
        }
    }

}