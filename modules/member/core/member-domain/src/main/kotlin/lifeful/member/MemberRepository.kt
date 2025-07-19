package lifeful.member

import lifeful.shared.id.MemberId

/**
 * 사용자 저장소
 */
interface MemberRepository {
    fun save(member: Member): Member
    fun findById(id: MemberId): Member?
}