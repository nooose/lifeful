package lifeful.member

import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.MemberId

data class Member(
    val email: Email,
    val nickname: String,
    val passwordHash: String,
    val status: MemberStatus = MemberStatus.PENDING,
    val id: MemberId = MemberId(),
    override val createdAt: Instant = Instant.now(),
    override val modifiedAt: Instant = createdAt,
) : BaseModel {
    val isActive: Boolean
        get() = status == MemberStatus.ACTIVE

    fun activate(): Member {
        require(status == MemberStatus.PENDING) { "PENDING 상태가 아닙니다." }

        return copy(status = MemberStatus.ACTIVE)
    }

    fun deactivate(): Member {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }

        return copy(status = MemberStatus.DEACTIVATED)
    }

    fun matchesPassword(
        rawPassword: String,
        passwordEncoder: PasswordEncoder,
    ): Boolean {
        return passwordEncoder.matches(rawPassword, passwordHash)
    }

    fun changePassword(
        rawPassword: String,
        passwordEncoder: PasswordEncoder,
    ): Member {
        return copy(
            passwordHash = passwordEncoder.encode(rawPassword),
        )
    }

    companion object {
        fun register(
            id: MemberId,
            email: Email,
            nickname: String,
            password: String,
            passwordEncoder: PasswordEncoder,
        ): Member {
            return Member(
                id = id,
                email = email,
                nickname = nickname,
                passwordHash = passwordEncoder.encode(password),
            )
        }
    }
}
