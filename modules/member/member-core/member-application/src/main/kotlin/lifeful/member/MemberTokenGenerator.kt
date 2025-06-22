package lifeful.member

import java.util.Date

interface MemberTokenGenerator {
    fun generate(
        member: MemberPublicModel,
        issuedAt: Date,
    ): String
}
