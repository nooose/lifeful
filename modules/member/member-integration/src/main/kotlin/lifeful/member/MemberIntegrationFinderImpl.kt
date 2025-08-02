package lifeful.member

import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class MemberIntegrationFinderImpl(
    private val memberFinder: MemberIntegrationFinder,
) : MemberIntegrationFinder {
    override fun all(ids: List<MemberId>): List<MemberPublicModel> {
        return memberFinder.all(ids)
            .map {
                MemberPublicModel(
                    id = it.id,
                    nickname = it.nickname,
                )
            }
    }

    override fun byId(id: MemberId): MemberPublicModel? {
        return memberFinder.byId(id)?.let {
            MemberPublicModel(
                id = it.id,
                nickname = it.nickname,
            )
        }
    }
}
