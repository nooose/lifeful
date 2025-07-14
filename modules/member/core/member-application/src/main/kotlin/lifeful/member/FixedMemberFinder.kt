package lifeful.member

import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class FixedMemberFinder : MemberFinder {
    override fun all(ids: List<MemberId>): List<MemberPublicModel> {
        return ids.map {
            MemberPublicModel(
                id = it,
                name = it.toString(),
            )
        }
    }

    override fun byId(id: MemberId): MemberPublicModel? {
        return MemberPublicModel(
            id = id,
            name = id.toString(),
        )
    }
}
