package lifeful.member

import lifeful.shared.MemberId
import org.springframework.modulith.NamedInterface

@NamedInterface("member-public-model")
data class MemberPublicModel(
    val id: MemberId,
    val name: String,
)
