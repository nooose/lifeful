package lifeful.member

import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class FixedMemberFinder : MemberIntegrationFinder {
    override fun all(ids: List<MemberId>): List<MemberPublicModel> {
        return ids.map {
            MemberPublicModel(
                id = it,
                nickname = it.toString(),
            )
        }
    }

    override fun byId(id: MemberId): MemberPublicModel? {
        return MemberPublicModel(
            id = id,
            nickname = id.toString(),
        )
    }
}
