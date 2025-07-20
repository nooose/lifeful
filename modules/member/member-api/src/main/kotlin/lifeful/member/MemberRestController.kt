package lifeful.member

import jakarta.validation.Valid
import lifeful.member.command.MemberRegister
import lifeful.shared.id.MemberId
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
    private val memberRegister: MemberRegister,
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
}
