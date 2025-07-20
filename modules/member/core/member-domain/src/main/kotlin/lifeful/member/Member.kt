package lifeful.member

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import lifeful.shared.id.MemberId
import lifeful.shared.model.BaseAggregateRootEntity

@Entity
class Member private constructor(
    val email: Email,
    var nickname: String,
    var passwordHash: String,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "member")
    val detail: MemberDetail,
    var status: MemberStatus = MemberStatus.ACTIVE,
) : BaseAggregateRootEntity<Member>() {
    val isActive: Boolean
        get() = status == MemberStatus.ACTIVE

    fun activate() {
        require(status == MemberStatus.ACTIVE) { "DEACTIVATE 상태가 아닙니다." }
        this.status = MemberStatus.ACTIVE
        detail.activate()
    }

    fun deactivate() {
        require(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아닙니다." }
        this.status = MemberStatus.DEACTIVATED
        detail.deactivate()
        registerEvent(MemberDeactivatedEvent(memberId = MemberId(id)))
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
            val detail = MemberDetail()
            val member = Member(
                email = email,
                nickname = nickname,
                passwordHash = passwordEncoder.encode(password),
                detail = detail,
            )
            detail.member = member
            return member
        }
    }
}
