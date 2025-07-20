package lifeful.member

import lifeful.member.command.MemberLogin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class AuthRestController(
    private val memberLogin: MemberLogin,
) : AuthApi {
    @PostMapping("/api/v1/auth/token")
    override fun getToken(request: TokenRequest): String {
        return memberLogin.getToken(request.toCommand())
    }
}
