package lifeful.member

import java.util.Date
import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service

@Service
class MemberLoginService(
    private val memberTokenGenerator: MemberTokenGenerator,
) {
    fun getToken(
        memberId: MemberId,
        issuedAt: Date,
    ): String {
        val member = findMember(memberId)
            ?: throw MemberNotFoundException("사용자($memberId)를 찾을 수 없습니다.")

        return memberTokenGenerator.generate(
            memberId = member.id,
            issuedAt = issuedAt,
        )
    }

    private fun findMember(memberId: MemberId): Member? {
        return Member(
            id = memberId,
            email = Email("test@test.com"),
            nickname = "test",
            passwordHash = "testPasswordHash",
        )
    }
}
