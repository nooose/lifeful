package lifeful.member

import java.util.Date
import lifeful.shared.id.MemberId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class AuthRestController(
    private val memberLogin: MemberLogin,
) : AuthApi {
    @PostMapping("/api/v1/auth/token")
    override fun getToken(request: TokenRequest): String {
        return memberLogin.getToken(
            memberId = MemberId(request.memberId),
            issuedAt = Date(),
        )
    }
}
