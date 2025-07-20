package lifeful.member

import lifeful.shared.id.MemberId

interface MemberIntegrationFinder {
    fun all(ids: List<MemberId>): List<MemberPublicModel>

    fun byId(id: MemberId): MemberPublicModel?
}
