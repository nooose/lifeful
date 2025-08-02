package lifeful.member

import lifeful.shared.id.MemberId

class MemberRegisteredEvent(
    val memberId: MemberId,
)

class MemberDeactivatedEvent(
    val memberId: MemberId,
)
