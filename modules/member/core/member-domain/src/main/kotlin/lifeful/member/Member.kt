package lifeful.member

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.MemberId

@Entity
class Member(
    val email: Email,
    var nickname: String,
    var passwordHash: String,
    var status: MemberStatus = MemberStatus.ACTIVE,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    override val createdAt: Instant = Instant.now(),
    override var modifiedAt: Instant = createdAt,
) : BaseModel {
    val isActive: Boolean
        get() = status == MemberStatus.ACTIVE

    fun activate() {
        this.status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }
        this.status = MemberStatus.DEACTIVATED
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
    ) {
        this.passwordHash = passwordEncoder.encode(rawPassword)
    }

    companion object {
        fun register(
            email: Email,
            nickname: String,
            password: String,
            passwordEncoder: PasswordEncoder,
        ): Member {
            return Member(
                email = email,
                nickname = nickname,
                passwordHash = passwordEncoder.encode(password),
            )
        }
    }
}
