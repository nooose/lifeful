package lifeful.member

import lifeful.member.query.MemberFinder
import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class MemberIntegrationFinderImpl(
    private val memberFinder: MemberFinder,
) : MemberIntegrationFinder {
    override fun all(ids: List<MemberId>): List<MemberPublicModel> {
        return memberFinder.findAll(ids)
            .map {
                MemberPublicModel(
                    id = MemberId(it.id),
                    nickname = it.nickname,
                )
            }
    }

    override fun byId(id: MemberId): MemberPublicModel? {
        return memberFinder.get(id).let {
            MemberPublicModel(
                id = MemberId(it.id),
                nickname = it.nickname,
            )
        }
    }
}
