package lifeful.member

import lifeful.shared.id.MemberId
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl : MemberRepository {
    override fun save(member: Member): Member {
        TODO("Not yet implemented")
    }

    override fun findById(id: MemberId): Member? {
        TODO("Not yet implemented")
    }
}