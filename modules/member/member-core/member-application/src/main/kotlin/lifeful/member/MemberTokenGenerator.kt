package lifeful.member

import java.util.*

interface MemberTokenGenerator {
    fun generate(
        member: MemberPublicModel,
        issuedAt: Date,
    ): String
}
