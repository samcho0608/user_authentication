package com.samcho.user_authentication

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import com.samcho.user_authentication.domain.user.service.AppUserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class UserAuthenticationApplication {
	@Bean
	fun run(appUserService: AppUserService) : CommandLineRunner {
		return CommandLineRunner {
			appUserService.signUp(
				AppUser().apply {
					nicknm = "Sam"
					name = "Sam"
					email = EmailAddress("organicyellow0608@gmail.com")
					password = "Yearbook101!"
					phoneNumber = PhoneNumber("821084273267")
				}
			)
		}
	}
}

fun main(args: Array<String>) {
	runApplication<UserAuthenticationApplication>(*args)
}
