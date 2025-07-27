package lifeful.member.command

import lifeful.shared.id.MemberId

data class MemberChangeNicknameCommand(
    val memberId: MemberId,
    val newNickname: String,
)
