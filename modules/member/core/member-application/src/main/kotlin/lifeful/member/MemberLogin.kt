package lifeful.member

import lifeful.shared.id.MemberId
import java.util.Date

interface MemberLogin {
    fun getToken(
        memberId: MemberId,
        issuedAt: Date,
    ): String
}