package com.samcho.user_authentication

import com.samcho.user_authentication.presentation.verification.VerificationController
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserAuthenticationApplicationTests @Autowired constructor(
	private val verificationController: VerificationController,
) {

	@Test
	fun contextLoads() {
		assertNotEquals(null, verificationController)
	}

}
