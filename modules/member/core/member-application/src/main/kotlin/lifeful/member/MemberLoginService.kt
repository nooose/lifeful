package lifeful.member

import java.util.Date
import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service

@Service
class MemberLoginService(
    private val memberFinder: MemberFinder,
    private val memberTokenGenerator: MemberTokenGenerator,
) {
    fun getToken(
        memberId: MemberId,
        issuedAt: Date,
    ): String {
        val member = memberFinder.byId(id = memberId)
            ?: throw MemberNotFoundException("사용자($memberId)를 찾을 수 없습니다.")
        return memberTokenGenerator.generate(member, issuedAt = issuedAt)
    }
}
