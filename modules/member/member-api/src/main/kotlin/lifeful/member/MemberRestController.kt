package lifeful.member

import jakarta.validation.Valid
import lifeful.member.command.MemberRegister
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
}
