package lifeful.member

import jakarta.validation.Valid
import lifeful.member.command.MemberChangeNickname
import lifeful.member.command.MemberRegister
import lifeful.member.query.MemberFinder
import lifeful.shared.id.MemberId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
    private val memberRegister: MemberRegister,
    private val memberFinder: MemberFinder,
    private val memberChangeNickname: MemberChangeNickname,
) : MemberApi {
    @PostMapping("/api/v1/members")
    override fun register(
        @RequestBody @Valid request: MemberRegisterRequest,
    ) {
        memberRegister.register(request.toCommand())
    }

    @PostMapping("/api/v1/members/{memberId}:deactivate")
    override fun deactivate(
        @PathVariable memberId: Long,
    ) {
        memberRegister.deactivate(MemberId(memberId))
    }

    @GetMapping("/api/v1/members")
    override fun findAll(): List<MemberResponse> {
        return memberFinder.findAll().map { MemberResponse.from(it) }
    }

    @PatchMapping("/api/v1/members/{memberId}/nickname")
    override fun changeNickname(
        @PathVariable memberId: Long,
        @RequestBody @Valid request: MemberChangeNicknameRequest,
    ) {
        memberChangeNickname.changeNickname(request.toCommand(memberId))
    }
}
