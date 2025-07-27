package lifeful.member.command

/**
 * 회원 닉네임 변경 유즈케이스
 */
interface MemberChangeNickname {
    fun changeNickname(command: MemberChangeNicknameCommand)
}
