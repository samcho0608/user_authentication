package com.samcho.user_authentication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserAuthenticationApplication

fun main(args: Array<String>) {
	runApplication<UserAuthenticationApplication>(*args)
}
