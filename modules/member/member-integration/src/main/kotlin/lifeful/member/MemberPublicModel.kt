package lifeful.member

import lifeful.shared.id.MemberId

data class MemberPublicModel(
    val id: MemberId,
    val nickname: String,
)
