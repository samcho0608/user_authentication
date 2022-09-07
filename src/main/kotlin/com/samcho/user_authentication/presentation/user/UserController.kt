package com.samcho.user_authentication.presentation.user

import com.samcho.user_authentication.domain.user.service.AppUserService
import com.samcho.user_authentication.presentation.ApiRoute
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class UserController @Autowired constructor(
    private val appUserService: AppUserService
) {

    @GetMapping(ApiRoute.USERS_MINE)
    fun getMyInfo() : ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    @PostMapping(ApiRoute.USERS)
    fun signUp(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: SignUpRequest
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    @PutMapping(ApiRoute.RESET_PASSWORD)
    fun resetPassword(@RequestBody request: ResetPasswordRequest): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    @GetMapping(ApiRoute.REFRESH_TOKEN)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

}