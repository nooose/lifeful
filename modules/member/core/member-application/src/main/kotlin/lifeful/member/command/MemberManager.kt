package lifeful.member.command

import java.util.Date
import lifeful.member.Email
import lifeful.member.Member
import lifeful.member.MemberAccessDeniedException
import lifeful.member.MemberAuthenticationFailedException
import lifeful.member.MemberRegisteredEvent
import lifeful.member.MemberRepository
import lifeful.member.MemberTokenGenerator
import lifeful.member.PasswordEncoder
import lifeful.member.query.MemberFinder
import lifeful.shared.exception.DuplicateException
import lifeful.shared.id.MemberId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
internal class MemberManager(
    private val memberRepository: MemberRepository,
    private val memberTokenGenerator: MemberTokenGenerator,
    private val eventPublisher: ApplicationEventPublisher,
    private val passwordEncoder: PasswordEncoder,
    private val memberFinder: MemberFinder,
) : MemberRegister, MemberLogin {
    override fun register(command: MemberRegisterCommand): Member {
        checkDuplicateEmail(command)

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

    private fun checkDuplicateEmail(command: MemberRegisterCommand) {
        val requestEmail = Email(command.email)
        val existsMember = memberFinder.find(requestEmail)
        require(existsMember == null) {
            throw DuplicateException("중복된 이메일이 존재합니다.")
        }
    }

    override fun deactivate(memberId: MemberId) {
        val member = memberFinder.get(memberId)

        member.deactivate()

        memberRepository.save(member)
    }

    override fun getToken(command: MemberLoginCommand): String {
        val member = checkEmailAndPassword(command)
        checkValidMember(member)

        return memberTokenGenerator.generate(
            memberId = MemberId(member.id),
            issuedAt = Date(),
        )
    }

    private fun checkEmailAndPassword(command: MemberLoginCommand): Member {
        val member = memberRepository.findByEmail(Email(command.email))

        require(member != null && member.matchesPassword(command.password, passwordEncoder)) {
            throw MemberAuthenticationFailedException("회원 정보가 일치하지 않습니다.")
        }

        return member
    }

    private fun checkValidMember(member: Member) {
        require(member.isActive) {
            throw MemberAccessDeniedException("활성화 사용자가 아닙니다.")
        }
    }
}
