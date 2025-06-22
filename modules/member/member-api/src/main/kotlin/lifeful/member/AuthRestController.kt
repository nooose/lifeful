package lifeful.member

import java.util.*
import lifeful.shared.MemberId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class AuthRestController(
    private val memberLoginService: MemberLoginService,
) : AuthApi {
    @PostMapping("/api/v1/auth/token")
    override fun getToken(request: TokenRequest): String {
        return memberLoginService.getToken(
            memberId = MemberId(request.memberId),
            issuedAt = Date(),
        )
    }
}
