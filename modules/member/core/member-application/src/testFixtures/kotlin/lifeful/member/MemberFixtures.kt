package lifeful.member

import lifeful.member.command.MemberRegisterCommand

class MemberFixtures {
    companion object {
        fun registerFixtures(
            nickname: String = "테스트",
            email: String = "test@test.com",
            password: String = "123456",
        ): MemberRegisterCommand {
            return MemberRegisterCommand(
                nickname = nickname,
                email = email,
                password = password,
            )
        }
    }
}
