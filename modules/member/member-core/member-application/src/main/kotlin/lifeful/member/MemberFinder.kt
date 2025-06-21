package lifeful.member

import lifeful.shared.MemberId
import org.springframework.modulith.NamedInterface

@NamedInterface("member-finder")
interface MemberFinder {
    fun all(ids: List<MemberId>): List<MemberPublicModel>
    fun byId(id: MemberId): MemberPublicModel?
}
