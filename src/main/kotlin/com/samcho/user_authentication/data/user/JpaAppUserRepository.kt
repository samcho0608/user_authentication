package com.samcho.user_authentication.data.user

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.repository.AppUserRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@ConditionalOnProperty(name = ["app_user_repository.jpa"], havingValue = "true")
interface JpaAppUserRepository : AppUserRepository, JpaRepository<AppUser, String> {

    @Modifying
    @Query("UPDATE AppUser u SET u.password = :newPassword WHERE u.id = :id")
    override fun updatePasswordById(@Param("id") id: String, @Param("newPassword") newPassword: String)
}