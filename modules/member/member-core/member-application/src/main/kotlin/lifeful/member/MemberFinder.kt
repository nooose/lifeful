package lifeful.member

import lifeful.shared.MemberId

interface MemberFinder {
    fun all(ids: List<MemberId>): List<MemberPublicModel>
    fun byId(id: MemberId): MemberPublicModel?
}
