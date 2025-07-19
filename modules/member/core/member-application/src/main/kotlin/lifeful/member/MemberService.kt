package lifeful.member

import lifeful.shared.id.MemberId
import java.util.Date
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
internal class MemberService(
    private val memberRepository: MemberRepository,
    private val memberTokenGenerator: MemberTokenGenerator,
    private val eventPublisher: ApplicationEventPublisher,
    private val passwordEncoder: PasswordEncoder,
) : MemberRegister, MemberLogin {
    override fun register(command: MemberRegisterCommand): Member {
        val member = Member.register(
            email = Email(command.email),
            nickname = command.nickname,
            password = command.password,
            passwordEncoder = passwordEncoder,
        )
        val registeredMember = memberRepository.save(member)
        eventPublisher.publishEvent(MemberRegisteredEvent(memberId = MemberId(registeredMember.id)))
        return registeredMember
    }

    override fun getToken(
        command: MemberLoginCommand,
    ): String {
        val member = checkEmailAndPassword(command)
        checkValidMember(member)

        return memberTokenGenerator.generate(
            memberId = MemberId(member.id),
            issuedAt = Date(),
        )
    }

    private fun checkValidMember(member: Member) {
        require(member.isActive) {
            throw MemberAccessDeniedException("활성화 사용자가 아닙니다.")
        }
    }

    private fun checkEmailAndPassword(command: MemberLoginCommand): Member {
        val member = memberRepository.findByEmail(Email(command.email))
            ?: throw MemberAuthenticationFailedException("회원 정보가 일치하지 않습니다.")

        require(member.matchesPassword(command.password, passwordEncoder)) {
            throw MemberAuthenticationFailedException("회원 정보가 일치하지 않습니다.")
        }

        return member
    }
}
