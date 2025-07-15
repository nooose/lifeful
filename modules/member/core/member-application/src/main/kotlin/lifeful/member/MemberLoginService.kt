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

        val memberPublicModel = MemberPublicModel(
            id = member.id,
            name = member.name,
        )
        return memberTokenGenerator.generate(
            member = memberPublicModel,
            issuedAt = issuedAt,
        )
    }

    private fun findMember(memberId: MemberId): Member? {
        return Member(
            id = memberId,
            name = memberId.value.toString(),
        )
    }
}
