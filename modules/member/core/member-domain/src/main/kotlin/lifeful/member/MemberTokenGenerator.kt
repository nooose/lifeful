package lifeful.member

import java.util.Date
import lifeful.shared.id.MemberId

interface MemberTokenGenerator {
    fun generate(
        memberId: MemberId,
        issuedAt: Date,
    ): String
}
