package lifeful.member.command

import io.github.oshai.kotlinlogging.KotlinLogging
import lifeful.member.MemberRegisteredEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class MemberWelcomeService {
    private val log = KotlinLogging.logger {}

    @TransactionalEventListener
    fun handle(event: MemberRegisteredEvent) {
        log.info { "회원 가입 완료 $event" }
    }
}
