package com.samcho.user_authentication.presentation.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.samcho.user_authentication.domain.core.util.JwtFactory
import com.samcho.user_authentication.domain.core.util.logger
import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.service.AppUserService
import com.samcho.user_authentication.presentation.ApiRoute
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val userController: UserController,
    private val jwtFactory: JwtFactory,
    private val userService: AppUserService,
){

    @Test
    fun getMyInfo() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        userService.signUp(user)
        val at = jwtFactory.createToken(user.id!!, isAccessToken = true)

        mockMvc.perform(
            MockMvcRequestBuilders.get(ApiRoute.USERS_MINE)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $at")
        )
            .andDo { logger().debug("RECEIVED REQUEST : ${it.request.contentAsString}") }
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Success")))
    }

    @Test
    fun signUp() {
        val userEmail = "hgd1234@gmail.com"
        val userName = "홍길동"
        val userNicknm = "요리번쩍조리번쩍"
        val userPw = "hongGilDong1234"
        val userPhno = "821012341234"

        val user = AppUser().apply {
            email = EmailAddress(userEmail)
            name = userName
            nicknm = userNicknm
            password = userPw
            phoneNumber = PhoneNumber(
                userPhno
            )
        }

        val at = jwtFactory.createToken(user.phoneNumber!!.phoneNumber, isAccessToken = true)

        mockMvc.perform(
            MockMvcRequestBuilders.post(ApiRoute.USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $at")
                .content(
                    jacksonObjectMapper().writeValueAsString(
                        mapOf(
                            "nicname" to userNicknm,
                            "name" to userName,
                            "password" to userPw,
                            "eamil" to userEmail
                        )
                    )
                )
        )
            .andDo { logger().debug("RECEIVED REQUEST : ${it.request.contentAsString}") }
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Success")))
    }

    @Test
    fun resetPassword() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        userService.signUp(user)
        val at = jwtFactory.createToken(user.phoneNumber!!.phoneNumber, isAccessToken = true)

        mockMvc.perform(
            MockMvcRequestBuilders.put(ApiRoute.RESET_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $at")
                .content(
                    jacksonObjectMapper().writeValueAsString(
                        mapOf(
                            "password" to "new password",
                            "newPassword" to "new password",
                        )
                    )
                )
        )
            .andDo { logger().debug("RECEIVED REQUEST : ${it.request.contentAsString}") }
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Success")))
    }

    @Test
    fun refreshToken() {
        val user = AppUser().apply {
            email = EmailAddress("hgd1234@gmail.com")
            name = "홍길동"
            nicknm = "요리번쩍조리번쩍"
            password = "hongGilDong1234"
            phoneNumber = PhoneNumber(
                "821012341234"
            )
        }

        userService.signUp(user)
        val at = jwtFactory.createToken(user.id!!, isRefreshToken = true)

        mockMvc.perform(
            MockMvcRequestBuilders.get(ApiRoute.REFRESH_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $at")
        )
            .andDo { logger().debug("RECEIVED REQUEST : ${it.request.contentAsString}") }
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Success")))
    }
}