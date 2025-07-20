package lifeful.member.query

import lifeful.member.Email
import lifeful.member.Member
import lifeful.member.MemberNotFoundException
import lifeful.member.MemberRepository
import lifeful.shared.id.MemberId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
internal class MemberQueryService(
    private val memberRepository: MemberRepository,
) : MemberFinder {
    override fun find(email: Email): Member? {
        return memberRepository.findByEmail(email)
    }

    override fun get(id: MemberId): Member {
        return memberRepository.findById(id.value)
            ?: throw MemberNotFoundException("회원($id)을 찾을 수 없습니다.")
    }
}
