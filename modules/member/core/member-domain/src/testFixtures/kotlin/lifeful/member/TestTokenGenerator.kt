package lifeful.member

import java.util.Date
import lifeful.shared.id.MemberId

class TestTokenGenerator : MemberTokenGenerator {
    override fun generate(
        memberId: MemberId,
        issuedAt: Date,
    ): String {
        return "${issuedAt.time}-${memberId.value}"
    }
}
