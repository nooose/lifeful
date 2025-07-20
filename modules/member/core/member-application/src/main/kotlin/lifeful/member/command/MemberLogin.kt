package lifeful.member.command

/**
 * 회원 로그인 유즈케이스
 */
interface MemberLogin {
    fun getToken(command: MemberLoginCommand): String
}
