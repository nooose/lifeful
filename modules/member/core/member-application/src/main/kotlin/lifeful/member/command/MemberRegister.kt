package lifeful.member.command

import lifeful.member.Member
import lifeful.shared.id.MemberId

/**
 * 회원 등록 유즈케이스
 */
interface MemberRegister {
    fun register(command: MemberRegisterCommand): Member

    fun deactivate(memberId: MemberId)
}
