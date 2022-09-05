package com.samcho.user_authentication.domain.user.repository

import com.samcho.user_authentication.domain.user.AppUser
import com.samcho.user_authentication.domain.user.AppUserDetail

class MemoryAppUserRepository : AppUserRepository {
    override fun findById(id: String): AppUser? {
        TODO("Not yet implemented")
    }

    override fun findAppUserDetailById(id: String): AppUserDetail? {
        TODO("Not yet implemented")
    }

    override fun save(user: AppUser): AppUser {
        TODO("Not yet implemented")
    }

    /**
     *  테스트 시에만 사용되며, 저장된 데이터를 지움
     */
    internal fun clearDB() {
        TODO("Not yet implemented")
    }

}