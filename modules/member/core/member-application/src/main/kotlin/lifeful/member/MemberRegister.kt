package lifeful.member

/**
 * 회원 가입 유즈케이스
 */
interface MemberRegister {
    fun register(
        command: MemberRegisterCommand,
    ): Member
}