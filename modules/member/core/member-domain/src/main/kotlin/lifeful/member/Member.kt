package lifeful.member

import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.MemberId

data class Member(
    val id: MemberId,
    val name: String,
    override val createdAt: Instant = Instant.now(),
    override val modifiedAt: Instant = createdAt,
) : BaseModel
