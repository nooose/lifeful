package lifeful.member

import jakarta.validation.Valid
import lifeful.member.command.MemberLogin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController(
    private val memberLogin: MemberLogin,
) : AuthApi {
    @PostMapping("/api/v1/auth/token")
    override fun getToken(@RequestBody @Valid request: TokenRequest): String {
        return memberLogin.getToken(request.toCommand())
    }
}
