package lifeful.member.query

import lifeful.member.Email
import lifeful.member.Member
import lifeful.shared.id.MemberId

interface MemberFinder {
    fun find(email: Email): Member?

    fun get(id: MemberId): Member
}
