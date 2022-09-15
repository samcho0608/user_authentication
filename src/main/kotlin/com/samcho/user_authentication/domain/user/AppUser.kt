package com.samcho.user_authentication.domain.user

import com.samcho.user_authentication.domain.user.email_address.EmailAddress
import com.samcho.user_authentication.domain.user.phone_number.PhoneNumber
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

/**
 * 유저 정보 Entity
 */
@Entity
@Table(name = "USER_TBL")
class AppUser(
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(
        name = "prod-generator",
        strategy = "com.samcho.user_authentication.domain.user.UserIdGenerator"
    )
    var id: String? = null,

    /**
     * 유저 닉네임. Unique
     */
    @Column(name = "USER_NICKNM", unique = true)
    var nicknm: String = "",

    /**
     * 유저 이메일. Unique
     */
    @Embedded
    @AttributeOverrides(
        value = [
            AttributeOverride(
                name = "emailAddress",
                column = Column(
                    name = "USER_EMAIL",
                    unique = true
                )
            )
        ]
    )
    var email: EmailAddress? = null,
    /**
     * 유저 이름
     */
    @Column(name="USER_NAME")
    var name: String = "",
    /**
     * 비밀번호.
     */
    @Column(name="USER_PW")
    var password: String = "",
    /**
     * 유저 전화번호
     */
    @Embedded
    @AttributeOverrides(
        value = [
            AttributeOverride(
                name = "phoneNumber",
                column = Column(
                    name="USER_PHNO",
                    unique = true
                )
            )
        ]
    )
    var phoneNumber: PhoneNumber? = null,
) {
    /**
     * 유저 객체가 로그인에 필요한 정보를 충분하게 포함하고 있는가를 알려주는 함수
     */
    fun satisfiesLogInRequirements() =
        (phoneNumber != null || nicknm.isNotEmpty() || email != null) && password.isNotEmpty()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppUser

        if (id != other.id) return false
        if (nicknm != other.nicknm) return false
        if (email != other.email) return false
        if (phoneNumber != other.phoneNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + nicknm.hashCode()
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        return result
    }


}
