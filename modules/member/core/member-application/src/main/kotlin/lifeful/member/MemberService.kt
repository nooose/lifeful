package lifeful.member

import lifeful.shared.exception.UnauthorizedException
import java.util.Date
import lifeful.shared.id.MemberId
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
        val member = Member(
            email = Email(command.email),
            nickname = command.nickname,
            passwordHash = passwordEncoder.encode("testPasswordHash"),
        )
        val registeredMember = memberRepository.save(member)
        eventPublisher.publishEvent(MemberRegisteredEvent(memberId = registeredMember.id))
        return registeredMember
    }

    override fun getToken(
        memberId: MemberId,
        issuedAt: Date,
    ): String {
        val member = findMember(memberId)
            ?: throw MemberNotFoundException("사용자($memberId)를 찾을 수 없습니다.")

        require(member.matchesPassword("testPassword", passwordEncoder)) {
            throw UnauthorizedException("회원 정보가 일치하지 않습니다.")
        }

        return memberTokenGenerator.generate(
            memberId = member.id,
            issuedAt = issuedAt,
        )
    }

    private fun findMember(memberId: MemberId): Member? {
        return memberRepository.findById(memberId)
    }
}
