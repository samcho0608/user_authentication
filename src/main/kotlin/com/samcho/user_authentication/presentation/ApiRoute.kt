package com.samcho.user_authentication.presentation

abstract class ApiRoute {
    companion object {
        const val USERS = "/users"
        const val USERS_MINE = "/users/mine"
        const val LOG_IN = "/users/log-in"
        const val RESET_PASSWORD = "/users/reset-password"
        const val VERIFICATIONS = "/verifications"
        const val PHONE_VERIFICATIONS = "/verifications/phones"
        const val VERIFY_PHONE_VERIFICATIONS = "/verifications/phones/verify"
    }
}