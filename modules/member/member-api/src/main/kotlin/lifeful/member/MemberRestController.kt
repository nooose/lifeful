package lifeful.member

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class MemberRestController : MemberApi {
    @GetMapping("/api/v1/members")
    override fun getMembers(): List<Any> {
        return emptyList()
    }
}
